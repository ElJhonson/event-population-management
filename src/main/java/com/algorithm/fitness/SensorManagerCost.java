package com.algorithm.fitness;

/**
 * Manages sensor cost configuration for places and transitions in a Petri Net model.
 */
public class SensorManagerCost {

    private final float[] costPlaces;
    private final float[] costTransitions;

    /**
     * Creates a cost configuration for places and transitions.
     *
     * @param costPlaces      Cost array for each place in the Petri Net.
     * @param costTransitions Cost array for each transition in the Petri Net.
     */
    public SensorManagerCost(float[] costPlaces, float[] costTransitions) {
        this.costPlaces = costPlaces;
        this.costTransitions = costTransitions;
    }

    public float[] getCostPlaces() {
        return costPlaces;
    }

    public float[] getCostTransitions() {
        return costTransitions;
    }
}