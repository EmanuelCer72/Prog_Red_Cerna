package ar.edu.et32;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Ejercicio4 {
    private PrintStream ps = new PrintStream(System.out);

    public void resolverEjercicioA() {
        ps.println("\n--- 4.a: Suma y media de números ---");

        List<Integer> numeros = leerValores();
        if (numeros.isEmpty()) {
            ps.println("No se ingresaron valores.");
            return;
        }
        long suma = calcularSuma(numeros);
        mostrarResultados(numeros, suma);
    }


    public void resolverEjercicioB() {
        ps.println("\n--- 4.b: Colegio y nacionalidades ---");

        Map<String, Integer> colegio = new HashMap<>();

        addAlumno(colegio); // Llama al método para agregar alumnos
        showAll(colegio);
        showNacionalidad(colegio);
        cuantos(colegio);
        borra(colegio);
    }


    public void resolverEjercicioC() {
        ps.println("\n--- 4.c: Días de la semana ---");

        List<String> listDias = new ArrayList<>();
        Collections.addAll(listDias, "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");

        listDias.add(4, "Juernes");
        ps.println("Lista con 'Juernes': " + listDias);

        List<String> listaDos = new ArrayList<>(listDias);
        listDias.addAll(listaDos);
        ps.println("Lista original + copia: " + listDias);

        ps.println("Elemento en posición 3: " + listDias.get(3));
        ps.println("Elemento en posición 4: " + listDias.get(4));


        if (listDias.remove("Juernes")) ps.println("'Juernes' fue eliminado.");

        ps.println("Recorriendo con Iterador:");
        Iterator<String> it = listDias.iterator();
        while(it.hasNext()){
            ps.println("- " + it.next());
        }

        boolean encontrado = false;
        for(String dia : listDias) {
            if(dia.equalsIgnoreCase("lunes")) {
                encontrado = true;
                break;
            }
        }
        ps.println("¿Se encontró 'Lunes' (sin importar mayúsculas)?: " + encontrado);

        Collections.sort(listDias);
        ps.println("Lista ordenada: " + listDias);
    }

    public void resolverEjercicioD() {
        ps.println("\n--- 4.d: Conjunto de jugadores ---");

        Set<String> jugadores = new HashSet<>();
        Collections.addAll(jugadores, "Jordi Alba", "Pique", "Busquets", "Iniesta", "Messi");

        ps.println("Jugadores del equipo:");
        for(String jugador : jugadores) ps.println("- " + jugador);

        boolean existeNeymar = jugadores.contains("Neymar JR");
        ps.println("¿Existe 'Neymar JR' en el conjunto?: " + existeNeymar);

        Set<String> jugadores2 = new HashSet<>();
        Collections.addAll(jugadores2, "Pique", "Busquets");

        boolean existenTodos = jugadores.containsAll(jugadores2);
        ps.println("¿Existen todos los jugadores de jugadores2 en jugadores?: " + existenTodos);

        Set<String> union = new HashSet<>(jugadores);
        union.addAll(jugadores2);
        ps.println("Unión de los dos conjuntos: " + union);

        boolean seAgregoPique = jugadores.add("Pique");
        ps.println("¿Se pudo agregar a 'Pique' de nuevo?: " + seAgregoPique);
        ps.println("Conjunto final: " + jugadores);
    }


    public void resolverEjercicioE() {
        ps.println("\n--- 4.e: Bolas de dos colores ---");

        Set<Integer> bolasRojas = new HashSet<>();
        Random rand = new Random();

        while(bolasRojas.size() < 6) {
            bolasRojas.add(rand.nextInt(33) + 1); // Números del 1 al 33
        }

        int bolaAzul = rand.nextInt(16) + 1; // Número del 1 al 16

        ps.println(">>> Apuesta generada:");
        ps.println("Bolas Rojas: " + bolasRojas);
        ps.println("Bola Azul: " + bolaAzul);
    }


    private String leerTexto(String mensaje) {
        ps.print(mensaje + " ");
        try {
            return new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            return "";
        }
    }


    private List<Integer> leerValores() {
        List<Integer> lista = new ArrayList<>();
        while (true) {
            String entrada = leerTexto("Ingrese un número entero (-99 para terminar):");
            try {
                int num = Integer.parseInt(entrada);
                if (num == -99) break;
                lista.add(num);
            } catch (NumberFormatException e) {
                ps.println("Entrada no válida. Por favor, ingrese un número entero.");
            }
        }
        return lista;
    }

    private long calcularSuma(List<Integer> lista) {
        long suma = 0;
        for (int num : lista) {
            suma += num;
        }
        return suma;
    }

    private void mostrarResultados(List<Integer> lista, long suma) {
        double media = (double) suma / lista.size();
        int mayoresQueMedia = 0;
        for (int num : lista) {
            if (num > media) {
                mayoresQueMedia++;
            }
        }
        ps.println("\n--- Resultados ---");
        ps.println("Valores leídos: " + lista.size());
        ps.println("Suma: " + suma);
        ps.printf("Media: %.2f%n", media);
        ps.println("Valores leídos: " + lista);
        ps.println("Cantidad de valores mayores que la media: " + mayoresQueMedia);
    }


    private void addAlumno(Map<String, Integer> colegio) {
        while(true) {
            String nacionalidad = leerTexto("Ingrese nacionalidad del alumno ('fin' para terminar):").trim();
            if(nacionalidad.equalsIgnoreCase("fin")) break;
            if(nacionalidad.isEmpty()) continue;

            colegio.put(nacionalidad, colegio.getOrDefault(nacionalidad, 0) + 1);
        }
    }

    private void showAll(Map<String, Integer> colegio) {
        ps.println("\n--- Alumnos por Nacionalidad (showAll) ---");
        for (Map.Entry<String, Integer> entry : colegio.entrySet()) {
            ps.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void showNacionalidad(Map<String, Integer> colegio) {
        String nac = leerTexto("Ingrese una nacionalidad para ver sus detalles:");
        Integer cantidad = colegio.get(nac);
        if (cantidad != null) {
            ps.println("Detalle de " + nac + ": " + cantidad + " alumnos.");
        } else {
            ps.println("No hay alumnos de la nacionalidad " + nac + ".");
        }
    }

    private void cuantos(Map<String, Integer> colegio) {
        ps.println("Total de nacionalidades diferentes: " + colegio.size());
    }

    private void borra(Map<String, Integer> colegio) {
        colegio.clear();
        ps.println("Datos del colegio borrados. El mapa ahora tiene " + colegio.size() + " elementos.");
    }
}
