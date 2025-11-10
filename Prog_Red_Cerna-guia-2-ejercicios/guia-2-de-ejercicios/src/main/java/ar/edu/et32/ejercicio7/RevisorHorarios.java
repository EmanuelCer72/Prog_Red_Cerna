package ar.edu.et32.ejercicio7;

import javax.swing.JOptionPane;
import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;

public class RevisorHorarios implements Runnable {
    private BlockingQueue<Empleado> colaEmpleados;
    private static final LocalTime HORA_INGRESO = LocalTime.of(8, 0);

    public RevisorHorarios(BlockingQueue<Empleado> colaEmpleados) {
        this.colaEmpleados = colaEmpleados;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Empleado empleado = colaEmpleados.take();
                if (empleado == Empleado.POISON_PILL) {
                    // End of work
                    JOptionPane.showMessageDialog(null, "Todos los empleados han sido procesados. El programa se cerrará.");
                    break;
                }
                procesarEmpleado(empleado);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void procesarEmpleado(Empleado empleado) {
        String mensaje;
        if (empleado.getHoraIngreso().isAfter(HORA_INGRESO)) {
            mensaje = "El empleado " + empleado.getNombre() + " llegó TARDE el día " + empleado.getDia() + " a las " + empleado.getHoraIngreso();
        } else {
            mensaje = "El empleado " + empleado.getNombre() + " llegó TEMPRANO el día " + empleado.getDia() + " a las " + empleado.getHoraIngreso();
        }
        JOptionPane.showMessageDialog(null, mensaje);
    }
}
