package ar.edu.et32.ejercicio2;

import javax.swing.JOptionPane;

public class Contador extends Thread {
    private String nombre;
    private int limite;
    private int delay;

    public Contador(String nombre, int limite, int delay) {
        this.nombre = nombre;
        this.limite = limite;
        this.delay = delay;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= limite; i++) {
            System.out.println("Contador " + nombre + ": " + i);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        JOptionPane.showMessageDialog(null, "El contador '" + nombre + "' tardó " + duration + " ms en terminar.");
    }
}
