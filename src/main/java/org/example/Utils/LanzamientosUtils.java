package org.example.Utils;

import java.util.*;

public class LanzamientosUtils {

    public static List<List<String>> generarPlanVuelo() {
        char[][] trayecto = new char[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                trayecto[i][j] = ' ';
            }
        }
        Random rand = new Random();
        int x0 = rand.nextInt(100);
        int y0 = rand.nextInt(100);
        int x1 = rand.nextInt(100);
        int y1 = rand.nextInt(100);
        int x = x0;
        int y = y0;
        while (x != x1 || y != y1) {
            trayecto[x][y] = 'x';
            if (x != x1 && y != y1) {
                if (rand.nextBoolean()) {
                    x += (x1 > x) ? 1 : -1;
                } else {
                    y += (y1 > y) ? 1 : -1;
                }
            } else if (x != x1) {
                x += (x1 > x) ? 1 : -1;
            } else if (y != y1) {
                y += (y1 > y) ? 1 : -1;
            }
        }
        trayecto[x][y] = 'x';
        return convertCharMatrixToList(trayecto);
    }

    private static List<List<String>> convertCharMatrixToList(char[][] matrix) {
        List<List<String>> list = new ArrayList<>();

        for (char[] row : matrix) {
            List<String> stringRow = new ArrayList<>();
            for (char c : row) {
                stringRow.add(String.valueOf(c)); // Convierte cada char a String
            }
            list.add(stringRow);
        }

        return list;
    }

}

