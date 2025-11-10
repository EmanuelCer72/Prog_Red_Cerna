package ar.edu.et32.ejercicio4;

import java.util.Random;

public class Matriz {
    public static int[][] generarMatriz(int filas, int columnas) {
        int[][] matriz = new int[filas][columnas];
        Random random = new Random();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = random.nextInt(10); // Simple values
            }
        }
        return matriz;
    }

    public static int[][] multiplicarSecuencial(int[][] a, int[][] b) {
        int filasA = a.length;
        int columnasA = a[0].length;
        int columnasB = b[0].length;
        int[][] resultado = new int[filasA][columnasB];

        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                for (int k = 0; k < columnasA; k++) {
                    resultado[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return resultado;
    }

    public static String matrizToString(int[][] matriz) {
        StringBuilder sb = new StringBuilder();
        for (int[] fila : matriz) {
            for (int val : fila) {
                sb.append(String.format("%5d", val));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
