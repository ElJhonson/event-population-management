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

    private static final Random RANDOM = new Random();

    /**
     * Performs mutation on the provided sensor configuration by randomly flipping
     * a "place" and a "transition" in the configuration with a 1% chance.
     * If a mutation occurs, the fitness of the mutated configuration is recalculated.
     *
     * @param sensorConfig The sensor configuration to mutate.
     * @return The mutated sensor configuration, or the original if no mutation occurred.
     */
    public static SensorConfig mutation(SensorConfig sensorConfig) {

        // Clone the original configuration to avoid modifying the original object
        SensorConfig nSC = sensorConfig.clone();

        // 1% chance for mutation
        if (RANDOM.nextInt(100) < 1) {
            int placeSize = nSC.getPlaceConfig().length;
            int transitionSize = nSC.getTransConfig().length;

            // Select random mutation points for places and transitions
            int mutationPointPlaces = RANDOM.nextInt(placeSize);
            int mutationPointTransitions = RANDOM.nextInt(transitionSize);

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

            return nSC;
        }

        // If no mutation occurs, return the cloned configuration
        return nSC;
    }
}