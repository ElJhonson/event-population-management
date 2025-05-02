package com.jhonson.service.algotithm;

import com.jhonson.service.algotithm.evolution.EvolutionManager;
import com.jhonson.service.algotithm.fitness.SensorManagerCost;
import com.jhonson.service.algotithm.model.SensorConfig;
import com.jhonson.service.algotithm.util.PopulationUtils;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Path al archivo CSV (por ahora está comentado)
        String CSVPath = "csvPath..";

        // Aquí podrías parsear un archivo CSV, descomentando la siguiente línea:
        // int[][] c = CsvParser.parseCsvToMatrix(CSVPath, ",");

        // Matriz de ejemplo que representa las relaciones entre lugares y transiciones.
        int[][] d = {
                {-1, 1, 0, 0},
                {1, -1, 1, 0},
                {0, 1, -1, -1},
                {0, 0, 1, -1},
                {-1, 0, 0, 1}
        };

        // Costos predefinidos para lugares y transiciones (pueden ajustarse según la aplicación)
        final float[] COST_PLACES = {1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
        final float[] COST_TRANSITION = {1.0f, 1.0f, 1.0f, 1.0f};

        // Configura los costos en el SensorManagerCost
        SensorManagerCost.CostConfig(COST_PLACES, COST_TRANSITION);

        // Evoluciona las generaciones de configuraciones de sensores (ajusta la cantidad de generaciones)
        List<List<SensorConfig>> scf = EvolutionManager.evolveGenerations(d, 3, 50);

        // Imprime las configuraciones por cada generación
        for (List<SensorConfig> sc : scf) {
            System.out.println("========================Generation=============================");

            // Muestra cada configuración de sensor dentro de la generación
            for (SensorConfig s : sc) {
                System.out.println(s);
            }

            // Muestra las configuraciones mínimas dentro de esta generación
            System.out.println("---------------- Min Values ----------------");
            PopulationUtils.getSensorConfigMin(sc).forEach(System.out::println);

            // Muestra las frecuencias de las configuraciones en esta generación
            System.out.println("---------------- Frequencies ----------------");
            PopulationUtils.printFrequencies(sc);

            System.out.println("--------------------------------------------");
        }
    }
}
