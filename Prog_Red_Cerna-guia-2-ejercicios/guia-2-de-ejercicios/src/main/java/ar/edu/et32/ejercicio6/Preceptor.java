package ar.edu.et32.ejercicio6;

import java.util.List;
import java.util.Random;

public class Preceptor implements Runnable {
    private OficinaDeAlumnos oficina;
    private Random random = new Random();

    public Preceptor(OficinaDeAlumnos oficina) {
        this.oficina = oficina;
    }

    @Override
    public void run() {
        System.out.println("Preceptor comienza a cargar asistencias...");
        List<Alumno> alumnos = oficina.getAlumnos();
        for (Alumno alumno : alumnos) {
            int asistencia = random.nextInt(101); // 0 to 100
            alumno.setAsistencia(asistencia);
            // The logic for AlumnoLibre is here
            alumno.setEsAlumnoRegular(asistencia >= 75);
             try {
                Thread.sleep(100); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Preceptor terminó.");
    }
}
