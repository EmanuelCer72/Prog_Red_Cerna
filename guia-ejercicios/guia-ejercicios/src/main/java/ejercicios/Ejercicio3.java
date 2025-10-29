package ar.edu.et32;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio3 {
    private PrintStream ps = new PrintStream(System.out);



    public void resolverEjercicioA() {
        ps.println("\n--- 3.a: Guardar último dato ---");
        String dato = leerTexto("Ingrese un dato:");

        try (PrintWriter pw = new PrintWriter(new FileWriter("ultimo_dato.txt"))) {
            pw.print(dato);
            ps.println(">>> Dato guardado correctamente en 'ultimo_dato.txt'");
        } catch (IOException e) {
            ps.println("!!! Error al escribir el archivo: " + e.getMessage());
        }
    }

    public void resolverEjercicioB() {
        ps.println("\n--- 3.b: Guardar todos los números ---");

        try (PrintWriter pw = new PrintWriter(new FileWriter("todos_los_numeros.txt", true))) {
            while (true) {
                String dato = leerTexto("Ingrese un dato (o 'salir' para terminar):");
                if (dato.equalsIgnoreCase("salir")) break;

                // Validación de número usando try-catch, un concepto fundamental.
                try {
                    Integer.parseInt(dato); // Intenta convertir, si falla, salta al catch.
                    pw.println(dato);
                    ps.println(">>> Número guardado.");
                } catch (NumberFormatException e) {
                    ps.println(">>> '" + dato + "' no es un número, no se guardará.");
                }
            }
        } catch (IOException e) {
            ps.println("!!! Error al escribir el archivo: " + e.getMessage());
        }
    }

    public void resolverEjercicioC() {
        ps.println("\n--- 3.c: Crear archivo de pares ---");
        try (PrintWriter pw = new PrintWriter(new FileWriter("numeros.txt"))) {
            for (int i = 0; i <= 1000; i += 2) {
                pw.println(i);
            }
            ps.println(">>> Archivo 'numeros.txt' creado con los pares del 0 al 1000.");
        } catch (IOException e) {
            ps.println("!!! Error al crear el archivo: " + e.getMessage());
        }
    }

    public void resolverEjercicioD() {
        ps.println("\n--- 3.d: Leer archivo de pares ---");

        try (BufferedReader br = new BufferedReader(new FileReader("numeros.txt"))) {
            String linea;
            ps.println(">>> Contenido de 'numeros.txt':");
            while ((linea = br.readLine()) != null) {
                ps.println(linea);
            }
        } catch (IOException e) {
            ps.println("!!! Error al leer el archivo: " + e.getMessage());
        }
    }

    public void resolverEjercicioE() {
        ps.println("\n--- 3.e: Borrar múltiplos de 3 ---");

        File original = new File("numeros.txt");
        File temporal = new File("numeros.tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(original));
             PrintWriter pw = new PrintWriter(new FileWriter(temporal))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                int numero = Integer.parseInt(linea);
                if (numero % 3 != 0) {
                    pw.println(linea);
                }
            }
        } catch (IOException | NumberFormatException e) {
            ps.println("!!! Error procesando el archivo: " + e.getMessage());
            return;
        }

        if (original.delete()) {
            if (temporal.renameTo(original)) {
                ps.println(">>> Se eliminaron los múltiplos de 3 del archivo 'numeros.txt'.");
            } else {
                ps.println("!!! Error al renombrar el archivo temporal.");
            }
        } else {
            ps.println("!!! Error al borrar el archivo original.");
        }
    }

    public void resolverEjercicioF() {
        ps.println("\n--- 3.f: Crear archivo de primos ---");
        try (BufferedReader br = new BufferedReader(new FileReader("numeros.txt"));
             PrintWriter pw = new PrintWriter(new FileWriter("primos.dat"))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                int numero = Integer.parseInt(linea);
                if (esPrimo(numero)) {
                    pw.println(numero);
                }
            }
            ps.println(">>> Archivo 'primos.dat' creado con los números primos.");
        } catch (IOException | NumberFormatException e) {
            ps.println("!!! Error procesando archivos: " + e.getMessage());
        }
    }

    public void resolverEjercicioG() {
        ps.println("\n--- 3.g: Reemplazar 'ñ' ---");
        String nombreArchivo = "caracteres.dat";

        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            ps.println("Ingrese 10 palabras que contengan la letra 'ñ'.");
            for(int i = 0; i < 10; i++) {
                String palabra = leerTexto("Palabra " + (i + 1) + ":");
                pw.println(palabra);
            }
        } catch (IOException e) {
            ps.println("!!! Error al crear el archivo: " + e.getMessage());
            return;
        }

        ps.println("\n>>> Fichero original:");
        List<String> lineas = leerArchivoCompleto(nombreArchivo);
        for(String linea : lineas) ps.println(linea);

        for (int i = 0; i < lineas.size(); i++) {
            lineas.set(i, lineas.get(i).replace("ñ", "nie-nio"));
        }

        if(escribirArchivoCompleto(nombreArchivo, lineas)) {
            ps.println("\n>>> Fichero arreglado:");
            for(String linea : lineas) ps.println(linea);
        }
    }



    private String leerTexto(String mensaje) {
        ps.print(mensaje + " ");
        try {
            return new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            return "";
        }
    }

    private boolean esPrimo(int numero) {
        if (numero <= 1) return false;
        for (int i = 2; i * i <= numero; i++) {
            if (numero % i == 0) return false;
        }
        return true;
    }

    private List<String> leerArchivoCompleto(String nombreArchivo) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            ps.println("!!! Error al leer el archivo " + nombreArchivo);
        }
        return lineas;
    }

    private boolean escribirArchivoCompleto(String nombreArchivo, List<String> lineas) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (String linea : lineas) {
                pw.println(linea);
            }
            return true;
        } catch (IOException e) {
            ps.println("!!! Error al escribir en el archivo " + nombreArchivo);
            return false;
        }
    }
}
