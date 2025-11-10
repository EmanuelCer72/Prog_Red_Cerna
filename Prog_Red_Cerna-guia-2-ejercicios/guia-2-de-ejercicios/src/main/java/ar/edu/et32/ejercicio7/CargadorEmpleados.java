package ar.edu.et32.ejercicio7;

import javax.swing.JOptionPane;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.concurrent.BlockingQueue;

public class CargadorEmpleados implements Runnable {
    private BlockingQueue<Empleado> colaEmpleados;

    public CargadorEmpleados(BlockingQueue<Empleado> colaEmpleados) {
        this.colaEmpleados = colaEmpleados;
    }

    @Override
    public void run() {
        int totalEmpleados = 0;
        while (totalEmpleados <= 0) {
            try {
                String totalStr = JOptionPane.showInputDialog("¿Cuántos empleados va a ingresar?");
                if (totalStr == null) return; // User cancelled
                totalEmpleados = Integer.parseInt(totalStr);
                if (totalEmpleados <= 0) JOptionPane.showMessageDialog(null, "Ingrese un número positivo.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
            }
        }

        for (int i = 0; i < totalEmpleados; i++) {
            String nombre = JOptionPane.showInputDialog("Nombre del empleado " + (i + 1) + ":");
            if (nombre == null) break; // Cancelled

            String dia = JOptionPane.showInputDialog("Día de ingreso para " + nombre + ":");
            if (dia == null) break;

            LocalTime hora = null;
            while (hora == null) {
                String horaStr = JOptionPane.showInputDialog("Hora de ingreso para " + nombre + " (formato HH:mm):");
                if (horaStr == null) break;
                try {
                    hora = LocalTime.parse(horaStr);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(null, "Formato de hora inválido. Use HH:mm.");
                }
            }
            if (hora == null) break;

            try {
                colaEmpleados.put(new Empleado(nombre, dia, hora));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Signal the end
        try {
            colaEmpleados.put(Empleado.POISON_PILL);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
