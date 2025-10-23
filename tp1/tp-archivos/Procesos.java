package tp_files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Procesos {

    public void procesarVector(int[] vector) {
        try (
            BufferedWriter resultados = new BufferedWriter(new FileWriter("resultados.txt", false));
            BufferedWriter errores = new BufferedWriter(new FileWriter("error.txt", false));
        ) {
            for (int i = 0; i < vector.length; i++) {
                try {
                    int dividendo = vector[i];
                    int divisor = vector[i + 1];

                    int resultado = dividendo / divisor;

                    resultados.write(dividendo + " / " + divisor + " = " + resultado);
                    resultados.newLine();

                } catch (ArithmeticException e) {
                    errores.write("Error matemático: " + vector[i] + "" + vector[i + 1] + e.toString());
                    errores.newLine();
                } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                    errores.write("Error de índice o valor nulo en posición " + i + ": " + e.toString());
                    errores.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al escribir archivos de salida: " + e.getMessage());
        }
    }
}
