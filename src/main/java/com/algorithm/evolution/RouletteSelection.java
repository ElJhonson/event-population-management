package com.algorithm.evolution;


import com.algorithm.model.SensorConfig;

import java.util.List;
import java.util.Random;


/**
 * Implements the roulette wheel selection algorithm to choose a sensor configuration from a list.
 * The probability of selection is inversely proportional to the fitness value of each sensor configuration.
 * A higher fitness value results in a lower probability of selection.
 */
public class RouletteSelection {

    /**
     * Selects a sensor configuration using the roulette wheel selection algorithm.
     * The selection probability is based on the fitness values, with higher fitness having a lower chance of being selected.
     * The algorithm normalizes the probabilities and uses cumulative probabilities to make the selection.
     *
     * @param sensors A list of SensorConfig objects representing the population of sensor configurations.
     * @return A selected SensorConfig object based on roulette wheel selection.
     */
    public static SensorConfig selectRoulette(List<SensorConfig> sensors) {
        int n = sensors.size();
        double totalInverseFitness = 0;
        double[] probabilities = new double[n];

        // Find the minimum and maximum fitness values
        double minFitness = sensors.stream().mapToDouble(SensorConfig::getFitness).min().orElse(1.0);
        double maxFitness = sensors.stream().mapToDouble(SensorConfig::getFitness).max().orElse(1.0);

        // Exponential adjustment to give more difference to low fitness values
        for (int i = 0; i < n; i++) {
            double fitness = sensors.get(i).getFitness();
            probabilities[i] = Math.pow(maxFitness / fitness, 2); // Exponential adjustment
            totalInverseFitness += probabilities[i];
        }

        // Normalize the probabilities by dividing by the total sum
        for (int i = 0; i < n; i++) {
            probabilities[i] /= totalInverseFitness;
        }

        // Create the cumulative roulette wheel
        double[] cumulative = new double[n];
        cumulative[0] = probabilities[0];
        for (int i = 1; i < n; i++) {
            cumulative[i] = cumulative[i - 1] + probabilities[i];
        }

        // Generate a random number between 0 and 1
        Random rand = new Random();
        double rnd = rand.nextDouble();

        // Find the corresponding range in the cumulative array
        for (int i = 0; i < n; i++) {
            if (rnd <= cumulative[i]) {
                return sensors.get(i);
            }
        }

        return sensors.get(n - 1); // Fallback to return the last one
    }
}

