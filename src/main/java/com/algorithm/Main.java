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

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        Sensor Placement Optimizer — Genetic Algorithm        ║");
        System.out.println("║     Petri Net-based optimization for event detectability     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("Matrix size: " + matrix.length + " places x " + matrix[0].length + " transitions");
        System.out.println();

        SensorConfig globalBest = null;

        // Evolve sensor configurations over multiple independent runs
        for (int i = 0; i < 10; i++) {
            System.out.println("┌─────────────────────────────────────────────────────────────┐");
            System.out.println("│                          Run " + (i + 1) + " of 10                          │");
            System.out.println("└─────────────────────────────────────────────────────────────┘");

            List<SensorConfig> bestPerGeneration = EvolutionManager.evolveGenerations(matrix, 100, 1000, fitnessCalculator);

            // Show fitness evolution every 10 generations
            System.out.println("  Fitness evolution (best per generation):");
            for (int g = 0; g < bestPerGeneration.size(); g++) {
                if (g % 10 == 0) {
                    System.out.printf("  Generation %3d → Fitness: %.1f%n", g, bestPerGeneration.get(g).getFitness());
                }
            }

            // Get the best config of this run
            SensorConfig runBest = bestPerGeneration.stream()
                    .min(SensorConfig::compareTo)
                    .orElseThrow();

            System.out.println();
            System.out.println("  ── Best result this run ──────────────────────────────────");
            System.out.println("  Fitness:             " + runBest.getFitness());
            System.out.println("  Sensor places:       " + OneZeroToPositions.positionCount(runBest.getPlaceConfig()));
            System.out.println("  Sensor transitions:  " + OneZeroToPositions.positionCount(runBest.getTransConfig()));
            System.out.println("  Transition vector:   " + Arrays.toString(
                    OneZeroToPositions.positionCountInverse(
                            OneZeroToPositions.positionCount(runBest.getTransConfig()),
                            runBest.getTransConfig().length
                    )
            ));
            System.out.println();

            // Track global best across all runs
            if (globalBest == null || runBest.getFitness() < globalBest.getFitness()) {
                globalBest = runBest;
            }
        }

        // Print the global best result across all runs
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     Global Best Result                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("  Fitness:             " + globalBest.getFitness());
        System.out.println("  Sensor places:       " + OneZeroToPositions.positionCount(globalBest.getPlaceConfig()));
        System.out.println("  Sensor transitions:  " + OneZeroToPositions.positionCount(globalBest.getTransConfig()));
        System.out.println("  Transition vector:   " + Arrays.toString(
                OneZeroToPositions.positionCountInverse(
                        OneZeroToPositions.positionCount(globalBest.getTransConfig()),
                        globalBest.getTransConfig().length
                )
        ));
        System.out.println("  Total sensors used:  " +
                (OneZeroToPositions.positionCount(globalBest.getPlaceConfig()).size() +
                        OneZeroToPositions.positionCount(globalBest.getTransConfig()).size()));
    }
}