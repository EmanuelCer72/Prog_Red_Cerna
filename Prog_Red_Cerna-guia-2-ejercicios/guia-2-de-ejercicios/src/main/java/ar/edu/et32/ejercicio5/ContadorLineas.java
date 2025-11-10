package ar.edu.et32.ejercicio5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ContadorLineas implements Runnable {
    private File archivo;
    private Totalizador totalizador;

    public ContadorLineas(File archivo, Totalizador totalizador) {
        this.archivo = archivo;
        this.totalizador = totalizador;
    }

    @Override
    public void run() {
        int lineas = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            while (reader.readLine() != null) {
                lineas++;
            }
            totalizador.agregarLineas(lineas);
            System.out.println("Hilo para " + archivo.getName() + " contó " + lineas + " líneas.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
