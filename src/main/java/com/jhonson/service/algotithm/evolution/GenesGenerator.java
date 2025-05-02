package com.jhonson.service.algotithm.evolution;

import com.jhonson.service.algotithm.fitness.EventDetectabilityChecker;
import com.jhonson.service.algotithm.model.PlaceTransitionGenerator;
import com.jhonson.service.algotithm.model.SensorConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates a list of valid sensor configurations by creating random places and transitions,
 * and ensuring that the generated configurations are event detectable.
 * This process repeats until enough valid configurations are generated.
 */
public class GenesGenerator {

    /**
     * Generates a list of valid sensor configurations for the system.
     * The generation process involves creating random places and transitions,
     * followed by checking if the configuration is event detectable.
     * Only valid configurations are added to the list.
     *
     * @param c A 2D array representing configuration parameters.
     * @return A list of valid SensorConfig objects.
     */
    public static List<SensorConfig> generateGenes(int[][] c, int populationSize) {
        List<SensorConfig> validConfigs = new ArrayList<>();  // List to store valid sensor configurations.

        // Continue generating configurations until we have at least 99 valid ones.
        while (validConfigs.size() < populationSize) {
            // Generate random measurable places and transitions.
            int[] places = PlaceTransitionGenerator.generateMeasurablePlaces(c.length);
            int[] transitions = PlaceTransitionGenerator.generateMeasurableTransitions(c[0].length);

            // Check if the generated configuration is event detectable.
            if (EventDetectabilityChecker.checkEventDetectability(c, places, transitions)) {
                // If valid, create a new SensorConfig object and add it to the list.
                SensorConfig config = new SensorConfig(places, transitions);
                validConfigs.add(config);
            }
        }

        return validConfigs;
    }
}

