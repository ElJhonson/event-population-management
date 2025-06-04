package com.jhonson.service.algotithm.util;

import com.jhonson.service.algotithm.model.SensorConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class that provides helper methods to analyze a population of SensorConfig objects,
 * particularly related to fitness evaluation.
 */
public class PopulationUtils {

    /**
     * Retrieves a list of sensor configurations (SensorConfig) that have the minimum fitness value
     * within the given population.
     *
     * <p>This method iterates through the entire population and compares each individual's fitness
     * value to the minimum value found. If the fitness matches the minimum, the configuration is added
     * to the result list.</p>
     *
     * @param population A list of SensorConfig objects representing the population.
     * @return A list of SensorConfig instances with the lowest fitness value.
     */
    public static List<SensorConfig> getSensorsConfigMin(List<SensorConfig> population) {
        List<SensorConfig> configs = new ArrayList<>();
        float minValue = getMinValue(population);
        population.forEach(n -> {
            if (n.getFitness() == minValue) {
                configs.add(n);
            }
        });
        return configs;
    }

    public static SensorConfig getSensorConfigMin(List<SensorConfig> population) {
        return Collections.min(population);
    }





    /**
     * Finds the minimum fitness value within a given population of sensor configurations.
     *
     * <p>This method uses the natural ordering of SensorConfig objects (assuming it implements Comparable)
     * to determine which individual has the lowest fitness.</p>
     *
     * @param population A list of SensorConfig objects.
     * @return The minimum fitness value found in the population.
     */
    public static float getMinValue(List<SensorConfig> population) {
        SensorConfig conf = Collections.min(population);
        return conf.getFitness();
    }

    /**
     * Finds the maximum fitness value within a given population of sensor configurations.
     *
     * <p>This method uses the natural ordering of SensorConfig objects (assuming it implements Comparable)
     * to determine which individual has the highest fitness.</p>
     *
     * @param population A list of SensorConfig objects.
     * @return The maximum fitness value found in the population.
     */
    public static float getMaxValue(List<SensorConfig> population) {
        SensorConfig conf = Collections.max(population);
        return conf.getFitness();
    }

    /**
     * Computes the frequency distribution of fitness values in the population.
     *
     * <p>Each fitness value is multiplied by 10 and rounded to the nearest integer
     * to build a histogram. The result is a list where the index represents the fitness
     * multiplied by 10 and the value at that index represents how many individuals have that fitness.</p>
     *
     * @param population A list of SensorConfig objects.
     * @return A list of integers representing the frequency of each fitness (scaled by 10).
     */
    public static List<Integer> getFitnessFrequency(List<SensorConfig> population) {
        int maxValue = (int) population.stream()
                .mapToDouble(n -> n.getFitness() * 10)
                .map(Math::ceil)
                .max()
                .orElse(0);

        List<Integer> frequencies = new ArrayList<>(Collections.nCopies(maxValue + 1, 0));

        for (SensorConfig sc : population) {
            int index = (int) Math.round(sc.getFitness() * 10);
            frequencies.set(index, frequencies.get(index) + 1);
        }

        return frequencies;
    }

    /**
     * Prints the frequency distribution of fitness values in the population.
     *
     * <p>It calls {@link #getFitnessFrequency(List)} to retrieve the histogram,
     * then prints each fitness value (as a float) and how many times it appears in the population.</p>
     *
     * @param population A list of SensorConfig objects.
     */
    public static void printFrequencies(List<SensorConfig> population) {
        List<Integer> frequencies = getFitnessFrequency(population);

        for (int i = 0; i < frequencies.size(); i++) {
            int count = frequencies.get(i);
            if (count > 0) {
                double fitness = i / 10.0;
                System.out.printf("Fitness: %.1f appears %d times%n", fitness, count);
            }
        }
    }
}
