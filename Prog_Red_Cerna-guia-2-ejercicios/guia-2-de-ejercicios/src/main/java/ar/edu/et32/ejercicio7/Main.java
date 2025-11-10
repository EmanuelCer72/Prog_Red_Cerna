package ar.edu.et32.ejercicio7;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Empleado> colaEmpleados = new LinkedBlockingQueue<>();

        Thread cargador = new Thread(new CargadorEmpleados(colaEmpleados));
        Thread revisor = new Thread(new RevisorHorarios(colaEmpleados));

        System.out.println("Iniciando sistema de control de empleados...");
        cargador.start();
        revisor.start();
    }
}
