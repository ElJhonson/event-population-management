package com.algorithm;

import com.algorithm.evolution.EvolutionManager;
import com.algorithm.fitness.FitnessCalculator;
import com.algorithm.fitness.SensorManagerCost;
import com.algorithm.model.SensorConfig;
import com.algorithm.util.CsvParser;
import com.algorithm.util.OneZeroToPositions;

import java.util.Arrays;
import java.util.List;

/**
 * Entry point for the genetic algorithm that optimizes sensor placement
 * for industrial process monitoring using Petri Net matrices.
 */
public class Main {

    /** Predefined placement costs for each place in the Petri Net. */
    private static final float[] COST_PLACES = {
            30f, 30f, 40f, 40f, 50f, 50f, 40f, 40f, 40f, 40f,
            50f, 40f, 50f, 30f, 40f, 50f, 40f, 40f, 40f, 50f,
            40f, 50f, 50f, 50f, 50f, 50f, 50f, 35f, 35f, 35f,
            35f, 50f, 50f, 50f
    };

    /** Predefined placement costs for each transition in the Petri Net. */
    private static final float[] COST_TRANSITION = {
            300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f,
            300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f
    };

    public static void main(String[] args) {

        // Example matrix representing place-transition relationships in the Petri Net
        int[][] d = {
                {-1, 1, 0, 0},
                {1, -1, 1, 0},
                {0, 1, -1, -1},
                {0, 0, 1, -1},
                {-1, 0, 0, 1}
        };

        // Attempt to load matrix from CSV; fall back to example matrix if loading fails
        String csvPath = "C://Users//angel//OneDrive//Escritorio//matriz.csv";
        int[][] c = CsvParser.parseCsvToMatrix(csvPath, ",");
        int[][] matrix = (c.length > 0) ? c : d;

        // Create cost configuration and fitness calculator
        SensorManagerCost costConfig = new SensorManagerCost(COST_PLACES, COST_TRANSITION);
        FitnessCalculator fitnessCalculator = new FitnessCalculator(costConfig);

        // Evolve sensor configurations over multiple runs
        for (int i = 0; i < 10; i++) {
            System.out.println("================================= Run: " + (i + 1) + " =================================");

            List<SensorConfig> bestPerGeneration = EvolutionManager.evolveGenerations(matrix, 100, 1000, fitnessCalculator);

            int generation = 1;
            for (SensorConfig config : bestPerGeneration) {
                System.out.println("====================== Generation: " + generation++ + " ======================");
                System.out.println("Fitness:  " + config.getFitness());
                System.out.println("Places:   " + OneZeroToPositions.positionCount(config.getPlaceConfig()));
                System.out.println("Trans:    " + OneZeroToPositions.positionCount(config.getTransConfig()));
                System.out.println("Inverse:  " + Arrays.toString(
                        OneZeroToPositions.positionCountInverse(
                                OneZeroToPositions.positionCount(config.getTransConfig()),
                                config.getTransConfig().length
                        )
                ));
            }
        }
    }
}