package com.jhonson.service.algotithm;

import com.jhonson.service.algotithm.evolution.EvolutionManager;
import com.jhonson.service.algotithm.evolution.SensorMutation;
import com.jhonson.service.algotithm.fitness.SensorManagerCost;
import com.jhonson.service.algotithm.model.SensorConfig;
import com.jhonson.service.algotithm.util.CsvParser;
import com.jhonson.service.algotithm.util.OneZeroToPositions;
import com.jhonson.service.algotithm.util.PopulationUtils;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Path al archivo CSV (por ahora está comentado)
        String CSVPath = "C://Users//angel//OneDrive//Escritorio//matriz.csv";


        int[][] c = CsvParser.parseCsvToMatrix(CSVPath, ",");


        // Matriz de ejemplo que representa las relaciones entre lugares y transiciones.
        int[][] d = {
                {-1, 1, 0, 0},
                {1, -1, 1, 0},
                {0, 1, -1, -1},
                {0, 0, 1, -1},
                {-1, 0, 0, 1}
        };

        // Costos predefinidos para lugares y transiciones (pueden ajustarse según la aplicación)
        final float[] COST_PLACES = {
                30f, 30f, 40f, 40f, 50f, 50f, 40f, 40f, 40f, 40f,
                50f, 40f, 50f, 30f, 40f, 50f,40f, 40f, 40f, 50f,
                40f, 50f, 50f, 50f, 50f, 50f, 50f, 35f, 35f, 35f,
                35f,50f, 50f, 50f
        };

        final float[] COST_TRANSITION = {
                300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f,
                300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f
        };

//        final float[] COST_PLACES =     {1f, 1f, 1f, 1f, 1f};
//        final float[] COST_TRANSITION = {1f, 1f, 1f, 1f};
        SensorManagerCost.CostConfig(COST_PLACES, COST_TRANSITION);


        // Evoluciona las generaciones de configuraciones de sensores (ajusta la cantidad de generaciones)
        for (int i = 0; i < 10; i++) {
            System.out.println("================================= Time: "+(i+1)+ "=================================");
//            List<List<SensorConfig>> scf = EvolutionManager.evolveGenerations(d, 10, 50);
//            //for (List<SensorConfig> sc : scf) {
//                //System.out.println("========================Generation=============================");
//
//                // Muestra cada configuración de sensor dentro de la generación
//                for (SensorConfig s : sc) {
//                    System.out.println(s);
//                }
//
//                // Muestra las configuraciones mínimas dentro de esta generación
//            System.out.println("---------------- Min Values ----------------");
//            PopulationUtils.getSensorsConfigMin(sc).forEach(System.out::println);
//
//                // Muestra las frecuencias de las configuraciones en esta generación
//            System.out.println("---------------- Frequencies ----------------");
//            PopulationUtils.printFrequencies(sc);
//
//                //System.out.println("--------------------------------------------");
//            //}
            List<List<SensorConfig>> scf = EvolutionManager.evolveGenerations(c, 100, 1000);
            for (List<SensorConfig> sc : scf) {
                System.out.println("====================== Next Generation ======================");
                //System.out.println("Size: " + sc.size());


                //System.out.println("Menor fitness de cada generación");
                int j = 1;

                System.out.println(PopulationUtils.getSensorConfigMin(sc).getFitness());
                SensorConfig config = PopulationUtils.getSensorConfigMin(sc);
                System.out.println(OneZeroToPositions.positionCount(config.getPlaceConfig()));
                System.out.println(OneZeroToPositions.positionCount(config.getTransConfig()));
                System.out.println("Inversa");
                System.out.println(Arrays.toString(OneZeroToPositions.positionCountInverse(OneZeroToPositions.positionCount(config.getTransConfig()), config.getTransConfig().length)));

//                System.out.println(OneZeroToPositions.positionCount(s.getPlaceConfig()));
//                System.out.println(OneZeroToPositions.positionCount(s.getTransConfig()));
//                System.out.println("Fitenss: "+s.getFitness());
//                System.out.println("--------------------------------------");


            }
        }
//            System.out.println(scf.size());
//            System.out.println("Menor fitness de cada generación");
//            int j = 1;
//            for (List<SensorConfig> sc : scf){
//                System.out.println("** Generation "+(j++)+ " ** " + PopulationUtils.getSensorConfigMin(sc).getFitness());
//            }

        SensorConfig prueba = new SensorConfig();


    }



}
