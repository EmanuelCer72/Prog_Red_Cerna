package ar.edu.et32.ejercicio6;

import java.util.Arrays;

public class Alumno {
    private String nombre;
    private String apellido;
    private int asistencia; // Porcentaje de 0 a 100
    private int[] notas = new int[3];
    private boolean esAlumnoRegular;
    private double promedio;

    public Alumno(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        // Default values
        this.asistencia = 0;
        Arrays.fill(this.notas, 0);
        this.esAlumnoRegular = false;
        this.promedio = 0.0;
    }

    // Getters and Setters
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getAsistencia() { return asistencia; }
    public void setAsistencia(int asistencia) { this.asistencia = asistencia; }
    public int[] getNotas() { return notas; }
    public void setNotas(int[] notas) { this.notas = notas; }
    public boolean isEsAlumnoRegular() { return esAlumnoRegular; }
    public void setEsAlumnoRegular(boolean esAlumnoRegular) { this.esAlumnoRegular = esAlumnoRegular; }
    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }

    @Override
    public String toString() {
        return apellido + ", " + nombre;
    }
}
