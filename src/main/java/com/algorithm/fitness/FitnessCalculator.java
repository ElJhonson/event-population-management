package com.algorithm.fitness;

import com.algorithm.model.SensorConfig;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Calculates fitness for sensor configurations based on place and transition costs.
 */
public class FitnessCalculator {

    private final SensorManagerCost costConfig;

    public FitnessCalculator(SensorManagerCost costConfig) {
        this.costConfig = costConfig;
    }

    /**
     * Evaluates fitness for an entire population of sensor configurations.
     *
     * @param population List of sensor configurations to evaluate.
     * @return The same list with fitness values set.
     */
    public List<SensorConfig> evaluatePopulationFitness(List<SensorConfig> population) {
        population.forEach(this::computeFitness);
        return population;
    }

    /**
     * Computes the fitness for a single sensor configuration.
     *
     * @param config The sensor configuration to evaluate.
     * @return The fitness value as a float.
     */
    public float computeFitness(SensorConfig config) {
        int[] placeConfig = config.getPlaceConfig();
        int[] transitionConfig = config.getTransConfig();

        double totalPlaceCost = IntStream.range(0, placeConfig.length)
                .mapToDouble(i -> placeConfig[i] * costConfig.getCostPlaces()[i])
                .sum();

        double totalTransitionCost = IntStream.range(0, transitionConfig.length)
                .mapToDouble(i -> transitionConfig[i] * costConfig.getCostTransitions()[i])
                .sum();

        float fitness = (float) (totalPlaceCost + totalTransitionCost);
        config.setFitness(fitness);
        return fitness;
    }
}