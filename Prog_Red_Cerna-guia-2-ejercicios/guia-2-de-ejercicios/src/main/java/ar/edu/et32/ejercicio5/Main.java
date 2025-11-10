package ar.edu.et32.ejercicio5;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Seleccione la carpeta para contar líneas");

        int resultado = chooser.showOpenDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return; // User cancelled
        }

        File carpeta = chooser.getSelectedFile();
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt") ||
                                                            name.toLowerCase().endsWith(".java") ||
                                                            name.toLowerCase().endsWith(".md") ||
                                                            name.toLowerCase().endsWith(".xml") ||
                                                            name.toLowerCase().endsWith(".kt"));

        if (archivos == null || archivos.length == 0) {
            JOptionPane.showMessageDialog(null, "No se encontraron archivos de texto en la carpeta seleccionada.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Totalizador totalizador = new Totalizador();
        List<Thread> hilos = new ArrayList<>();

        for (File archivo : archivos) {
            Thread hilo = new Thread(new ContadorLineas(archivo, totalizador));
            hilos.add(hilo);
            hilo.start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        String mensaje = "Se encontraron " + archivos.length + " archivos.\n" +
                         "Trabajaron " + hilos.size() + " hilos.\n" +
                         "El total de renglones es: " + totalizador.getTotalLineas();

        JOptionPane.showMessageDialog(null, mensaje, "Resultado del Conteo", JOptionPane.INFORMATION_MESSAGE);
    }
}
