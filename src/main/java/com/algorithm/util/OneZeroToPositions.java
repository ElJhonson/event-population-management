package com.algorithm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OneZeroToPositions {
    public static List<Integer> positionCount(int[] oneZero){
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i <= oneZero.length-1; i++) {
            if (oneZero[i] == 1) {
                positions.add(i+1);
            }
        }
        return positions;
    }

    public static int[] positionCountInverse(List<Integer> positions, int size){
       int[] oneZero = new int[size];
       Arrays.fill(oneZero, 0);
        for (int i = 0; i < positions.size(); i++) {
            oneZero[positions.get(i)-1] = 1;
        }
        return oneZero;
    }

}
