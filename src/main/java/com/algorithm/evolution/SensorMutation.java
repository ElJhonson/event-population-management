package com.algorithm.evolution;

import com.algorithm.fitness.FitnessCalculator;
import com.algorithm.model.SensorConfig;

import java.util.Random;

/**
 * Implements the mutation operation on a given sensor configuration.
 * A mutation occurs with a 1% chance (randomly) by flipping a "place" and a "transition" in the sensor configuration.
 * The fitness of the mutated configuration is recalculated.
 */
public class SensorMutation {

    /**
     * Performs mutation on the provided sensor configuration by randomly flipping
     * a "place" and a "transition" in the configuration with a 1% chance.
     * If a mutation occurs, the fitness of the mutated configuration is recalculated.
     *
     * @param sensorConfig The sensor configuration to mutate.
     * @return The mutated sensor configuration, or the original if no mutation occurred.
     */
    public static SensorConfig mutation(SensorConfig sensorConfig) {

        Random random = new Random();
        // 1% chance for mutation (random number between 0 and 99)
        int chance = random.nextInt(100);

        // Clone the original configuration to avoid modifying the original object
        SensorConfig nSC = sensorConfig.clone();

        // If mutation happens (when chance equals 50)
        if (chance == 50) {
            int placeSize = nSC.getPlaceConfig().length;
            int transitionSize = nSC.getTransConfig().length;

            // Select random mutation points for places and transitions
            int mutationPointPlaces = random.nextInt(placeSize);
            int mutationPointTransitions = random.nextInt(transitionSize);

            int[] scPlaces = nSC.getPlaceConfig();
            int[] scTransitions = nSC.getTransConfig();

            // Flip the values at the mutation points (0 -> 1 or 1 -> 0)
            scPlaces[mutationPointPlaces] = (scPlaces[mutationPointPlaces] == 0) ? 1 : 0;
            scTransitions[mutationPointTransitions] = (scTransitions[mutationPointTransitions] == 0) ? 1 : 0;

            // Set the mutated configurations back to the sensor config
            nSC.setPlaceConfig(scPlaces);
            nSC.setTransConfig(scTransitions);

            // Recalculate the fitness of the mutated sensor configuration
            FitnessCalculator.computeFitness(nSC);

            // Return the mutated configuration
            return nSC;
        }

        // If no mutation occurs, return the original configuration
        return nSC;
    }
}

