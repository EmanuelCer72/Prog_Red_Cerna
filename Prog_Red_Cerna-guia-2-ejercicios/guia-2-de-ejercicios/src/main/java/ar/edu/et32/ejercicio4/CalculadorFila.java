package ar.edu.et32.ejercicio4;

public class CalculadorFila implements Runnable {
    private final int[][] resultado;
    private final int[][] matrizA;
    private final int[][] matrizB;
    private final int fila;

    public CalculadorFila(int[][] resultado, int[][] matrizA, int[][] matrizB, int fila) {
        this.resultado = resultado;
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.fila = fila;
    }

    @Override
    public void run() {
        int columnasB = matrizB[0].length;
        int columnasA = matrizA[0].length;
        for (int j = 0; j < columnasB; j++) {
            for (int k = 0; k < columnasA; k++) {
                resultado[fila][j] += matrizA[fila][k] * matrizB[k][j];
            }
        }
    }
}
