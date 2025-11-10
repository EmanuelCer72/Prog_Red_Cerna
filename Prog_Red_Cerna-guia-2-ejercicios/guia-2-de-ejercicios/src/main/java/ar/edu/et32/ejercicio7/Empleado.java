package ar.edu.et32.ejercicio7;

import java.time.LocalTime;

public class Empleado {
    private String nombre;
    private String dia;
    private LocalTime horaIngreso;

    // Special object to signal the end
    public static final Empleado POISON_PILL = new Empleado(null, null, null);

    public Empleado(String nombre, String dia, LocalTime horaIngreso) {
        this.nombre = nombre;
        this.dia = dia;
        this.horaIngreso = horaIngreso;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getDia() { return dia; }
    public LocalTime getHoraIngreso() { return horaIngreso; }
}
