package com.algorithm.util;

import com.algorithm.model.SensorConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class that provides helper methods to analyze a population of SensorConfig objects,
 * particularly related to fitness evaluation.
 */
public class PopulationUtils {

    /**
     * Retrieves a list of sensor configurations that have the minimum fitness value
     * within the given population.
     *
     * @param population A list of SensorConfig objects representing the population.
     * @return A list of SensorConfig instances with the lowest fitness value.
     */
    public static List<SensorConfig> getSensorsConfigMin(List<SensorConfig> population) {
        float minValue = getMinValue(population);
        return population.stream()
                .filter(n -> n.getFitness() == minValue)
                .toList();
    }

    /**
     * Retrieves the single sensor configuration with the minimum fitness value.
     *
     * @param population A list of SensorConfig objects.
     * @return The SensorConfig with the lowest fitness value.
     */
    public static SensorConfig getSensorConfigMin(List<SensorConfig> population) {
        return Collections.min(population);
    }

    /**
     * Finds the minimum fitness value within a given population.
     *
     * @param population A list of SensorConfig objects.
     * @return The minimum fitness value found in the population.
     */
    public static float getMinValue(List<SensorConfig> population) {
        return Collections.min(population).getFitness();
    }

    /**
     * Finds the maximum fitness value within a given population.
     *
     * @param population A list of SensorConfig objects.
     * @return The maximum fitness value found in the population.
     */
    public static float getMaxValue(List<SensorConfig> population) {
        return Collections.max(population).getFitness();
    }

    /**
     * Computes the frequency distribution of fitness values in the population
     * using a map to avoid memory waste with large fitness values.
     *
     * @param population A list of SensorConfig objects.
     * @return A map where keys are fitness values and values are their frequencies.
     */
    public static Map<Float, Integer> getFitnessFrequency(List<SensorConfig> population) {
        Map<Float, Integer> frequencies = new HashMap<>();
        for (SensorConfig sc : population) {
            float fitness = sc.getFitness();
            frequencies.put(fitness, frequencies.getOrDefault(fitness, 0) + 1);
        }
        return frequencies;
    }

    /**
     * Prints the frequency distribution of fitness values in the population.
     *
     * @param population A list of SensorConfig objects.
     */
    public static void printFrequencies(List<SensorConfig> population) {
        Map<Float, Integer> frequencies = getFitnessFrequency(population);
        frequencies.forEach((fitness, count) ->
                System.out.printf("Fitness: %.1f appears %d times%n", fitness, count));
    }
}