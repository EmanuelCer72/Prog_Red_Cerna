package ar.edu.et32.ejercicio6;

import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class Docente implements Runnable {
    private OficinaDeAlumnos oficina;
    private Random random = new Random();

    public Docente(OficinaDeAlumnos oficina) {
        this.oficina = oficina;
    }

    @Override
    public void run() {
        System.out.println("Docente comienza a cargar notas...");
        List<Alumno> alumnos = oficina.getAlumnos();
        for (Alumno alumno : alumnos) {
            int[] notas = {random.nextInt(10) + 1, random.nextInt(10) + 1, random.nextInt(10) + 1}; // 1 to 10
            alumno.setNotas(notas);
            // The logic for Promedio is here
            double promedio = Arrays.stream(notas).average().orElse(0.0);
            alumno.setPromedio(promedio);
            try {
                Thread.sleep(100); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Docente terminó.");
    }
}
