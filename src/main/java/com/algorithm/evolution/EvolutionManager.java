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
     * Only the best configuration per generation is stored to avoid memory accumulation.
     *
     * @param c                   A 2D array representing the Petri Net matrix.
     * @param numberOfGenerations The number of generations to evolve the population through.
     * @param populationSize      The number of sensor configurations per generation.
     * @param fitnessCalculator   The fitness calculator instance used to evaluate configurations.
     * @return A list of the best SensorConfig per generation.
     */
    public static List<SensorConfig> evolveGenerations(int[][] c, int numberOfGenerations, int populationSize, FitnessCalculator fitnessCalculator) {
        long startTime = System.nanoTime();
        List<SensorConfig> bestPerGeneration = new ArrayList<>();

        // Generate the initial population and evaluate fitness
        List<SensorConfig> currentPopulation = GenesGenerator.generateGenes(c, populationSize);
        fitnessCalculator.evaluatePopulationFitness(currentPopulation);

        // Store the best of the initial population
        bestPerGeneration.add(getBest(currentPopulation));

        // Evolve the population for the specified number of generations
        for (int i = 1; i <= numberOfGenerations; i++) {
            currentPopulation = nextGenerationBuilder(c, currentPopulation, fitnessCalculator);
            bestPerGeneration.add(getBest(currentPopulation));
        }

        long durationInMillis = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("Total evolution time: " + durationInMillis + " ms");
        return bestPerGeneration;
    }

    /**
     * Returns the best sensor configuration from a population.
     *
     * @param population The population to search.
     * @return The SensorConfig with the lowest fitness value.
     */
    private static SensorConfig getBest(List<SensorConfig> population) {
        return population.stream()
                .min(SensorConfig::compareTo)
                .orElseThrow();
    }

    /**
     * Builds the next generation of sensor configurations based on the current population.
     * This method uses roulette selection for parent selection, performs crossover and mutation,
     * and checks for event detectability to ensure valid children are added to the next generation.
     *
     * @param c                  A 2D array representing the Petri Net matrix.
     * @param currentPopulation  The population of SensorConfig objects for the current generation.
     * @param fitnessCalculator  The fitness calculator instance used to evaluate configurations.
     * @return A list of SensorConfig objects representing the next generation.
     */
    public static List<SensorConfig> nextGenerationBuilder(int[][] c, List<SensorConfig> currentPopulation, FitnessCalculator fitnessCalculator) {
        List<SensorConfig> nextGeneration = new ArrayList<>();

        // Generate children until the next generation is the same size as the current population
        while (nextGeneration.size() < currentPopulation.size()) {
            SensorConfig sc  = RouletteSelection.selectRoulette(currentPopulation); // Select parent 1
            SensorConfig sc1 = RouletteSelection.selectRoulette(currentPopulation); // Select parent 2

            // Perform crossover and mutation to generate children
            for (SensorConfig child : SensorCrossover.performCrossover(sc, sc1, fitnessCalculator)) {
                SensorConfig offSpring = SensorMutation.mutation(child, fitnessCalculator);

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