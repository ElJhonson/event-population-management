package com.algorithm.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Utility class for parsing CSV files and converting them into a matrix of integers.
 */
public class CsvParser {

    /**
     * Parses a CSV file and converts its contents into a 2D integer matrix.
     *
     * @param filePath  The path to the CSV file.
     * @param delimiter The delimiter used to separate values in the CSV file (e.g., ",", ";", "\t").
     * @return A 2D integer matrix representing the contents of the CSV file.
     * @throws IOException If an error occurs while reading the file.
     */
    public static int[][] parseCsvToMatrix(String filePath, String delimiter) {

        List<int[]> matrixData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line from the CSV file
            while ((line = reader.readLine()) != null) {
                // Split the line into string values using the given delimiter
                String[] valuesStr = line.split(delimiter);
                int[] valuesInt = new int[valuesStr.length];

                // Convert string values to integers
                for (int i = 0; i < valuesStr.length; i++) {
                    valuesInt[i] = Integer.parseInt(valuesStr[i]);
                }

                // Add the row to the matrix
                matrixData.add(valuesInt);
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

        // Convert the list of rows into a 2D integer array
        return matrixData.toArray(new int[0][]);
    }
}
