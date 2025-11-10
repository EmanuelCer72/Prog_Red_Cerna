package ar.edu.et32.ejercicio3;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Carrera {
    private JTextArea areaCarrera;
    private volatile boolean ganador = false;
    private Animal tortuga;
    private Animal liebre;
    private Map<Character, String> pistas = new HashMap<>();

    public Carrera() {
        JFrame frame = new JFrame("Carrera de la Liebre y la Tortuga");
        areaCarrera = new JTextArea(20, 80);
        areaCarrera.setEditable(false);
        areaCarrera.setFont(new Font("Monospaced", Font.PLAIN, 12));
        frame.add(new JScrollPane(areaCarrera));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        areaCarrera.setText("¡COMIENZA LA CARRERA!\n" + "------------------------------------------------------------------------\n");
    }

    public void setAnimales(Animal tortuga, Animal liebre) {
        this.tortuga = tortuga;
        this.liebre = liebre;
    }

    public synchronized void actualizarPista(Animal animal) {
        if (ganador) return;

        char inicial = animal.getInicial();
        int posicion = animal.getPosicion();

        StringBuilder linea = new StringBuilder();
        for (int i = 1; i < posicion; i++) {
            linea.append(" ");
        }
        linea.append(inicial);
        for (int i = posicion; i < 70; i++) {
            linea.append(" ");
        }
        linea.append("|"); // Finish line

        pistas.put(inicial, linea.toString());

        StringBuilder currentStatus = new StringBuilder();
        currentStatus.append(pistas.getOrDefault('T', " |") + "\n");
        currentStatus.append(pistas.getOrDefault('L', " |") + "\n");
        currentStatus.append("------------------------------------------------------------------------\n");

        areaCarrera.append(currentStatus.toString());
        areaCarrera.setCaretPosition(areaCarrera.getDocument().getLength());
    }

    public synchronized void verificarGanador() {
        if (ganador) return;

        boolean tortugaGano = tortuga.getPosicion() >= 70;
        boolean liebreGano = liebre.getPosicion() >= 70;

        String mensaje = "";
        if (tortugaGano && liebreGano) {
            mensaje = "\n¡EMPATE!";
            ganador = true;
        } else if (tortugaGano) {
            mensaje = "\n¡" + tortuga.getName() + " HA GANADO LA CARRERA!";
            ganador = true;
        } else if (liebreGano) {
            mensaje = "\n¡" + liebre.getName() + " HA GANADO LA CARRERA!";
            ganador = true;
        }

        if (ganador) {
            areaCarrera.append(mensaje);
            areaCarrera.setCaretPosition(areaCarrera.getDocument().getLength());
        }
    }

    public boolean hayGanador() {
        return ganador;
    }

    public static void main(String[] args) {
        Carrera carrera = new Carrera();
        Animal tortuga = new Animal("Tortuga", 'T', carrera);
        Animal liebre = new Animal("Liebre", 'L', carrera);
        carrera.setAnimales(tortuga, liebre);
        tortuga.start();
        liebre.start();
    }
}
