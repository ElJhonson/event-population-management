package com.jhonson.service.algotithm.model;

import java.util.Random;

/**
 * Utility class to generate random measurable places (states) and measurable transitions.
 * This class provides methods to create random configurations for places (states) and transitions,
 * which can be used for testing or generating sensor configurations in a Petri net model.
 */
public class PlaceTransitionGenerator {

    // Static instance of Random for generating random values.
    private static final Random random = new Random();

    /**
     * Generates a random array representing measurable places (states).
     * Each state is randomly assigned a value of 0 or 1.
     *
     * @param rows The number of places (states) to generate.
     * @return An array of length {@code rows} containing random 0s and 1s.
     */
    public static int[] generateMeasurablePlaces(int rows) {
        int[] states = new int[rows];

        // Assign random values (0 or 1) to each state
        for (int r = 0; r < rows; r++) {
            states[r] = random.nextInt(2); // Generates either 0 or 1 randomly
        }

        return states;
    }

    /**
     * Generates a random array representing measurable transitions.
     * Each transition is randomly assigned a value of 0 or 1.
     *
     * @param columns The number of transitions to generate.
     * @return An array of length {@code columns} containing random 0s and 1s.
     */
    public static int[] generateMeasurableTransitions(int columns) {
        int[] transitions = new int[columns];

        // Assign random values (0 or 1) to each transition
        for (int c = 0; c < columns; c++) {
            transitions[c] = random.nextInt(2); // Generates either 0 or 1 randomly
        }

        return transitions;
    }
}
