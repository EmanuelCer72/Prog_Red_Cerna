package ar.edu.et32.ejercicio6;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class OficinaDeAlumnos {
    private List<Alumno> alumnos = Collections.synchronizedList(new ArrayList<>());

    public void cargarAlumnos() {
        alumnos.add(new Alumno("Juan", "Perez"));
        alumnos.add(new Alumno("Ana", "Gomez"));
        alumnos.add(new Alumno("Luis", "Garcia"));
        alumnos.add(new Alumno("Maria", "Lopez"));
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }
}
