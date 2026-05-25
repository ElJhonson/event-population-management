package com.jhonson.service.algotithm.fitness;

/**
 * Utility class for managing the cost configuration of places and transitions
 * in a Petri net model. This class provides static arrays to store the cost
 * values for places and transitions, which are used for evaluating the fitness
 * of sensor configurations in the system.
 */
public class SensorManagerCost {

    /**
     * Static array that stores the cost of each place in the Petri net.
     * Each index in the array corresponds to a specific place in the net.
     */
    public static float[] COST_PLACES;

    /**
     * Static array that stores the cost of each transition in the Petri net.
     * Each index in the array corresponds to a specific transition in the net.
     */
    public static float[] COST_TRANSITION;

    /**
     * Configures the cost arrays for places and transitions. This method
     * should be called to set up the cost values before using them in fitness calculations.
     *
     * @param placeCost An array representing the cost for each place in the Petri net.
     * @param transitionsCost An array representing the cost for each transition in the Petri net.
     */
    public static void CostConfig(float[] placeCost, float[] transitionsCost) {
        COST_PLACES = placeCost;
        COST_TRANSITION = transitionsCost;
    }
}
