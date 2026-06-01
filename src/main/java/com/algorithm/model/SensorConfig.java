package com.algorithm.model;

import java.util.Arrays;

/**
 * Represents a configuration of sensors in a Petri net model.
 * The class holds the configuration of places and transitions, along with its fitness value.
 * It implements Comparable to allow sorting based on fitness and Cloneable for object cloning.
 */
public class SensorConfig implements Comparable<SensorConfig>, Cloneable {

    // Configuration of places in the sensor setup.
    private int[] placeConfig;

    // Configuration of transitions in the sensor setup.
    private int[] transConfig;

    // Fitness value of the sensor configuration, calculated based on the costs.
    private float fitness;

    /**
     * Default constructor for SensorConfig.
     * Initializes an empty SensorConfig object.
     */
    public SensorConfig() {
    }

    /**
     * Constructor to initialize SensorConfig with place and transition configurations.
     *
     * @param placeConfig  Array representing the configuration of places (states).
     * @param transConfig  Array representing the configuration of transitions.
     */
    public SensorConfig(int[] placeConfig, int[] transConfig) {
        this.placeConfig = placeConfig;
        this.transConfig = transConfig;
    }

    /**
     * Gets the configuration of places in the sensor setup.
     *
     * @return The place configuration as an integer array.
     */
    public int[] getPlaceConfig() {
        return placeConfig;
    }

    /**
     * Sets the configuration of places in the sensor setup.
     *
     * @param placeConfig The place configuration to set.
     */
    public void setPlaceConfig(int[] placeConfig) {
        this.placeConfig = placeConfig;
    }

    /**
     * Gets the configuration of transitions in the sensor setup.
     *
     * @return The transition configuration as an integer array.
     */
    public int[] getTransConfig() {
        return transConfig;
    }

    /**
     * Sets the configuration of transitions in the sensor setup.
     *
     * @param transConfig The transition configuration to set.
     */
    public void setTransConfig(int[] transConfig) {
        this.transConfig = transConfig;
    }

    /**
     * Gets the fitness value of the sensor configuration.
     *
     * @return The fitness value as a float.
     */
    public float getFitness() {
        return fitness;
    }

    /**
     * Sets the fitness value of the sensor configuration.
     *
     * @param fitness The fitness value to set.
     */
    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    /**
     * Returns a string representation of the SensorConfig object, including place, transition configurations, and fitness.
     *
     * @return A string representation of the sensor configuration.
     */
    @Override
    public String toString() {
        return "placeConfig: " + Arrays.toString(placeConfig) +
                "\ntransConfig: " + Arrays.toString(transConfig) +
                "\nfitness: " + fitness;
    }

    /**
     * Compares the current SensorConfig object with another based on their fitness values.
     * Used to sort SensorConfig objects in ascending order of fitness.
     *
     * @param o The other SensorConfig object to compare with.
     * @return A negative integer, zero, or a positive integer as this object's fitness is less than,
     *         equal to, or greater than the specified object's fitness.
     */
    @Override
    public int compareTo(SensorConfig o) {
        return Float.compare(this.fitness, o.fitness);
    }

    /**
     * Creates and returns a clone of this SensorConfig object.
     * The clone includes deep copies of the place and transition configurations.
     *
     * @return A clone of the current SensorConfig object.
     */
    @Override
    public SensorConfig clone() {
        try {
            // Creating a clone using the superclass's clone method
            SensorConfig cloned = (SensorConfig) super.clone();
            // Deep copy of the placeConfig and transConfig arrays
            cloned.placeConfig = Arrays.copyOf(this.placeConfig, this.placeConfig.length);
            cloned.transConfig = Arrays.copyOf(this.transConfig, this.transConfig.length);
            return cloned;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
