package ar.edu.et32.ejercicio6;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 1. Cargar alumnos
        OficinaDeAlumnos oficina = new OficinaDeAlumnos();
        oficina.cargarAlumnos();

        // 2. Crear y lanzar hilos
        Thread preceptorThread = new Thread(new Preceptor(oficina));
        Thread docenteThread = new Thread(new Docente(oficina));

        preceptorThread.start();
        docenteThread.start();

        // 3. Esperar a que terminen
        preceptorThread.join();
        docenteThread.join();
        
        System.out.println("Ambos hilos han terminado. Calculando resultados finales...");

        // 4. Informar resultados
        List<Alumno> alumnos = oficina.getAlumnos();
        StringBuilder eximidos = new StringBuilder("Alumnos Eximidos (Promedio >= 7 y Regulares):\n");
        boolean hayEximidos = false;

        for (Alumno alumno : alumnos) {
            if (alumno.isEsAlumnoRegular() && alumno.getPromedio() >= 7.0) {
                eximidos.append(String.format("- %s, %s: Nota Final %.2f\n",
                        alumno.getApellido(), alumno.getNombre(), alumno.getPromedio()));
                hayEximidos = true;
            }
        }
        
        if (!hayEximidos) {
            eximidos.append("No hay alumnos eximidos.");
        }

        // Also show all student data for context
        StringBuilder allData = new StringBuilder("--- Datos de Todos los Alumnos ---\n");
        for (Alumno alumno : alumnos) {
             allData.append(String.format("%s, %s: Asistencia: %d%%, Notas: %s, Prom: %.2f, Regular: %s\n",
                alumno.getApellido(), alumno.getNombre(), alumno.getAsistencia(),
                java.util.Arrays.toString(alumno.getNotas()), alumno.getPromedio(),
                alumno.isEsAlumnoRegular() ? "Sí" : "No"));
        }
        
        JOptionPane.showMessageDialog(null, allData.toString() + "\n" + eximidos.toString(), "Resultados Finales", JOptionPane.INFORMATION_MESSAGE);
    }
}
