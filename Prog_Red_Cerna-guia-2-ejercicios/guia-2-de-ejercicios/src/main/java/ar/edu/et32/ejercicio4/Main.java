package ar.edu.et32.ejercicio4;

import javax.swing.JOptionPane;

public class Main {
    private static final int SIZE = 4;

    public static void main(String[] args) throws InterruptedException {
        // Generar matrices
        int[][] matrizA = Matriz.generarMatriz(SIZE, SIZE);
        int[][] matrizB = Matriz.generarMatriz(SIZE, SIZE);

        StringBuilder info = new StringBuilder();
        info.append("Matriz A:\n").append(Matriz.matrizToString(matrizA));
        info.append("Matriz B:\n").append(Matriz.matrizToString(matrizB));

        // --- Versión Secuencial ---
        long startTime = System.nanoTime();
        int[][] resultadoSecuencial = Matriz.multiplicarSecuencial(matrizA, matrizB);
        long endTime = System.nanoTime();
        long duracionSecuencial = endTime - startTime;

        info.append("\n--- Resultado Secuencial ---\n");
        info.append(Matriz.matrizToString(resultadoSecuencial));
        info.append("Tiempo: ").append(duracionSecuencial).append(" ns\n");

        // --- Versión con Hilos ---
        int[][] resultadoHilos = new int[SIZE][SIZE];
        Thread[] hilos = new Thread[SIZE];

        startTime = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            hilos[i] = new Thread(new CalculadorFila(resultadoHilos, matrizA, matrizB, i));
            hilos[i].start();
        }

        for (int i = 0; i < SIZE; i++) {
            hilos[i].join();
        }
        endTime = System.nanoTime();
        long duracionHilos = endTime - startTime;

        info.append("\n--- Resultado con Hilos ---\n");
        info.append(Matriz.matrizToString(resultadoHilos));
        info.append("Tiempo: ").append(duracionHilos).append(" ns\n");
        
        // Comparar tiempos
        info.append("\nLa versión con hilos fue ");
        if (duracionHilos < duracionSecuencial) {
            info.append(String.format("%.2f", (double)duracionSecuencial / duracionHilos)).append(" veces más rápida.");
        } else {
            info.append(String.format("%.2f", (double)duracionHilos / duracionSecuencial)).append(" veces más lenta.");
        }


        JOptionPane.showMessageDialog(null, info.toString(), "Resultados Multiplicación de Matrices", JOptionPane.INFORMATION_MESSAGE);
    }
}
