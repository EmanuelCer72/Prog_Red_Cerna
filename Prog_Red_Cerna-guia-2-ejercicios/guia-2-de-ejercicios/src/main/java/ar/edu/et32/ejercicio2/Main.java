package ar.edu.et32.ejercicio2;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int numContadores = random.nextInt(8) + 3; // Random number between 3 and 10
        List<Contador> contadores = new ArrayList<>();

        System.out.println("Creando " + numContadores + " contadores...");

        for (int i = 1; i <= numContadores; i++) {
            String nombre = "C" + i;
            int limite = random.nextInt(5) + 5; // Random limit between 5 and 9
            int delay = random.nextInt(801) + 200; // Random delay between 200 and 1000 ms
            contadores.add(new Contador(nombre, limite, delay));
        }

        for (Contador contador : contadores) {
            contador.start();
        }
    }
}
