package com.jhonson.service.algotithm.fitness;

import com.jhonson.service.algotithm.model.SensorConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
/**
 * Class responsible for calculating the fitness function for a population of sensor configurations.
 * In the context of Petri Nets, it calculates the cost of places and transitions based on the number of sensors
 * and their respective costs.
 */
public class FitnessCalculator {

    /**
     * Evaluates the fitness function for a population of sensor configurations.
     * Each configuration consists of a distribution of sensors in places and transitions,
     * and the total cost is computed based on predefined costs.
     *
     * @param population is a List representing the population's sensor configurations.
     * @return A list of SensorConfig objects with calculated fitness values.
     */
    public static List<SensorConfig> evaluatePopulationFitness(List<SensorConfig> population) {
        population.forEach(config -> {
            // Calculate the fitness for each configuration and store it in the list.
            float fitness = computeFitness(config);
            config.setFitness(fitness);
        });

        return population;
    }

    /**
     * Computes the fitness for a single sensor configuration.
     * The fitness is calculated based on the number of sensors in places and transitions,
     * and their respective costs.
     *
     * @param config The sensor configuration to evaluate.
     * @return The fitness value as a float.
     */
    public static float computeFitness(SensorConfig config) {
        int[] placeConfig = config.getPlaceConfig();
        int[] transitionConfig = config.getTransConfig();

        // Calculate the total cost for the places.
        double totalPlaceCost = IntStream.range(0, placeConfig.length)
                .mapToDouble(i -> placeConfig[i] * SensorManagerCost.COST_PLACES[i])
                .sum();

        // Calculate the total cost for the transitions.
        double totalTransitionCost = IntStream.range(0, transitionConfig.length)
                .mapToDouble(i -> transitionConfig[i] * SensorManagerCost.COST_TRANSITION[i])
                .sum();

        // Return the total fitness (sum of place and transition costs).
        return (float) (totalPlaceCost + totalTransitionCost);
    }
}

