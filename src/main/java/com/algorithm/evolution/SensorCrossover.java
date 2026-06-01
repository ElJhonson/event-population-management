package com.algorithm.evolution;

import com.algorithm.fitness.FitnessCalculator;
import com.algorithm.model.SensorConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Performs the crossover operation between two sensor configurations to create offspring.
 * The crossover is performed with a given probability (70% chance).
 * The offspring inherit "places" and "transitions" configurations from both parents.
 */
public class SensorCrossover {

    private static final Random RANDOM = new Random();

    /**
     * Performs the crossover between two parent sensor configurations to generate offspring.
     * The crossover happens with a 70% chance, where a random crossover point is chosen for both
     * the "places" and "transitions" arrays. The offspring inherit configurations from both parents.
     * If crossover does not occur, the parents are returned as offspring.
     *
     * @param parent1           The first parent sensor configuration.
     * @param parent2           The second parent sensor configuration.
     * @param fitnessCalculator The fitness calculator instance used to evaluate offspring.
     * @return A list of the generated offspring sensor configurations, or the parents if no crossover happens.
     */
    public static List<SensorConfig> performCrossover(SensorConfig parent1, SensorConfig parent2, FitnessCalculator fitnessCalculator) {

        // Determine the crossover chance (70% chance for crossover)
        int chance = RANDOM.nextInt(1000);

        if (chance < 700) {
            int placeSize = parent1.getPlaceConfig().length;
            int transitionSize = parent1.getTransConfig().length;

            // Select crossover points for "places" and "transitions"
            int crossoverPointPlaces = RANDOM.nextInt(placeSize);
            int crossoverPointTransitions = RANDOM.nextInt(transitionSize);

            // Arrays for offspring configurations
            int[] placesChild1 = new int[placeSize];
            int[] placesChild2 = new int[placeSize];
            int[] transitionsChild1 = new int[transitionSize];
            int[] transitionsChild2 = new int[transitionSize];

            // Crossover for "places" configuration
            for (int i = 0; i < placeSize; i++) {
                if (i < crossoverPointPlaces) {
                    placesChild1[i] = parent1.getPlaceConfig()[i]; // Inherit from Parent 1
                    placesChild2[i] = parent2.getPlaceConfig()[i]; // Inherit from Parent 2
                } else {
                    placesChild1[i] = parent2.getPlaceConfig()[i]; // Swap with Parent 2
                    placesChild2[i] = parent1.getPlaceConfig()[i]; // Swap with Parent 1
                }
            }

            // Crossover for "transitions" configuration
            for (int i = 0; i < transitionSize; i++) {
                if (i < crossoverPointTransitions) {
                    transitionsChild1[i] = parent1.getTransConfig()[i]; // Inherit from Parent 1
                    transitionsChild2[i] = parent2.getTransConfig()[i]; // Inherit from Parent 2
                } else {
                    transitionsChild1[i] = parent2.getTransConfig()[i]; // Swap with Parent 2
                    transitionsChild2[i] = parent1.getTransConfig()[i]; // Swap with Parent 1
                }
            }

            // Create new offspring configurations
            SensorConfig child1 = new SensorConfig(placesChild1, transitionsChild1);
            SensorConfig child2 = new SensorConfig(placesChild2, transitionsChild2);

            List<SensorConfig> children = Arrays.asList(child1, child2);

            // Evaluate the fitness of the offspring
            return fitnessCalculator.evaluatePopulationFitness(children);
        }

        // If no crossover, return the parents as the offspring
        return Arrays.asList(parent1, parent2);
    }
}