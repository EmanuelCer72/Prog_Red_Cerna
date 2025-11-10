package ar.edu.et32.ejercicio1;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        String tipoStr;
        int tipo = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            tipoStr = JOptionPane.showInputDialog("Ingrese el tipo de hilo a ejecutar (1 para números, 2 para letras):");
            if (tipoStr == null) {
                // User cancelled
                return;
            }
            try {
                tipo = Integer.parseInt(tipoStr);
                if (tipo == 1 || tipo == 2) {
                    entradaValida = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Ingrese 1 o 2.", "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese un número válido.", "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            }
        }

        HiloAlfanumerico hilo = new HiloAlfanumerico(tipo);
        Thread thread = new Thread(hilo);
        thread.start();
    }
}
