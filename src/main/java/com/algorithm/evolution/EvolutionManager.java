package com.algorithm.evolution;


import com.algorithm.fitness.EventDetectabilityChecker;
import com.algorithm.fitness.FitnessCalculator;
import com.algorithm.model.SensorConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the evolution process of sensor configurations over multiple generations.
 * This class is responsible for generating initial populations, evaluating their fitness,
 * and creating subsequent generations through selection, crossover, and mutation processes.
 */
public class EvolutionManager {

    /**
     * Evolves a population of sensor configurations for a given number of generations.
     * The evolution process involves generating initial genes, evaluating fitness, and
     * iteratively generating new generations.
     *
     * <p>This method orchestrates the full evolutionary process, from the initial population
     * to the final generation.</p>
     *
     * @param c                  A 2D array representing the configuration parameters.
     * @param numberOfGenerations The number of generations to evolve the population through.
     * @return A list of lists, where each inner list represents a population of SensorConfig
     *         for each generation.
     */
    public static List<List<SensorConfig>> evolveGenerations(int[][] c, int numberOfGenerations, int populationSize) {
        long startTime = System.nanoTime(); // Inicio del tiempo
        List<List<SensorConfig>> allGenerations = new ArrayList<>();

        // Generate the initial population and evaluate fitness
        List<SensorConfig> initialPopulation = GenesGenerator.generateGenes(c, populationSize);
        List<SensorConfig> evaluatedInitial = FitnessCalculator.evaluatePopulationFitness(initialPopulation);

        // Add the evaluated initial population to the generations list
        allGenerations.add(evaluatedInitial);

        // Evolve the population for the specified number of generations
        for (int i = 1; i <= numberOfGenerations; i++) {
            List<SensorConfig> nextGen = NextGenerationBuilder(c, allGenerations.get(i - 1));
            allGenerations.add(nextGen);
        }

        long endTime = System.nanoTime(); // Fin del tiempo
        long durationInMillis = (endTime - startTime) / 1_000_000;

        System.out.println("Tiempo total de evolución: " + durationInMillis + " ms");
        return allGenerations;
    }

    /**
     * Builds the next generation of sensor configurations based on the current population.
     * This method uses roulette selection for parent selection, performs crossover and mutation,
     * and checks for event detectability to ensure valid child are added to the next generation.
     *
     * @param c                  A 2D array representing the configuration parameters.
     * @param currentPopulation  The population of SensorConfig objects for the current generation.
     * @return A list of SensorConfig objects representing the next generation.
     */
    public static List<SensorConfig> NextGenerationBuilder(int[][] c, List<SensorConfig> currentPopulation) {
        List<SensorConfig> nextGeneration = new ArrayList<>();

        SensorConfig sc;
        SensorConfig sc1;

        // Generate child until the next generation is the same size as the current population
        while (nextGeneration.size() < currentPopulation.size()) {
            sc = RouletteSelection.selectRoulette(currentPopulation);  // Select parent 1
            sc1 = RouletteSelection.selectRoulette(currentPopulation); // Select parent 2

            // Perform crossover and mutation to generate child
            for (SensorConfig child : SensorCrossover.performCrossover(sc, sc1)) {

                SensorConfig offSpring = SensorMutation.mutation(child);
                // Check if the child is valid before adding to the next generation
                if (EventDetectabilityChecker.checkEventDetectability(c, offSpring.getPlaceConfig(), offSpring.getTransConfig())) {
                    nextGeneration.add(offSpring);
                }
                if (nextGeneration.size() >= currentPopulation.size()) {
                    break;
                }
            }
        }

        return nextGeneration;
    }
}

