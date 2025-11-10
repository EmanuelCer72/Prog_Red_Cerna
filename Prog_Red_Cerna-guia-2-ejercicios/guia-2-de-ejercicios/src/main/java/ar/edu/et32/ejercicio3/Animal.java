package ar.edu.et32.ejercicio3;

import java.util.Random;

public class Animal extends Thread {
    private char inicial;
    private int posicion;
    private Carrera carrera;
    private Random random;

    public Animal(String nombre, char inicial, Carrera carrera) {
        super(nombre); // Set thread name
        this.inicial = inicial;
        this.carrera = carrera;
        this.posicion = 1;
        this.random = new Random();
    }

    public int getPosicion() {
        return posicion;
    }
    
    public char getInicial() {
        return inicial;
    }

    @Override
    public void run() {
        carrera.actualizarPista(this);
        while (posicion < 70 && !carrera.hayGanador()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (carrera.hayGanador()) {
                break;
            }

            int prob = random.nextInt(100) + 1;
            int movimiento = 0;

            if (inicial == 'T') { // Tortuga
                if (prob <= 50) movimiento = 3;
                else if (prob <= 70) movimiento = -6;
                else movimiento = 1;
            } else { // Liebre
                if (prob <= 20) movimiento = 0;
                else if (prob <= 40) movimiento = 9;
                else if (prob <= 50) movimiento = -12;
                else if (prob <= 80) movimiento = 1;
                else movimiento = -2;
            }

            posicion += movimiento;
            if (posicion < 1) posicion = 1;
            if (posicion > 70) posicion = 70;

            carrera.actualizarPista(this);

            if (posicion >= 70) {
                carrera.verificarGanador();
            }
        }
    }
}
