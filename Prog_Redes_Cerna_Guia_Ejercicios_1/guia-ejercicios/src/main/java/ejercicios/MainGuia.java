package ar.edu.et32;

import java.io.PrintStream;

/**
 * Clase principal que contiene el menú infinito para ejecutar los ejercicios.
 * LÓGICA DE CLASE: Basado en el archivo 'menu.java' y 'EjemploResolucionTPs/main.java'
 * para la estructura del while(true) y el switch.
 */
public class MainGuia {
    public static void main(String[] args) {
        PrintStream ps = new PrintStream(System.out);

        // Creamos un objeto para cada clase de ejercicios
        Ejercicio3 ejerciciosPunto3 = new Ejercicio3();
        Ejercicio4 ejerciciosPunto4 = new Ejercicio4();

        while (true) {
            ps.println("\n------------------------------------");
            ps.println("--- GUÍA DE EJERCICIOS I/O ---");
            ps.println("------------------------------------");
            ps.println("--- PUNTO 3: Archivos ---");
            ps.println("31. Guardar último dato");
            ps.println("32. Guardar todos los números");
            ps.println("33. Crear archivo de pares");
            ps.println("34. Leer archivo de pares");
            ps.println("35. Borrar múltiplos de 3");
            ps.println("36. Crear archivo de primos");
            ps.println("37. Reemplazar 'ñ'");
            ps.println("\n--- PUNTO 4: Colecciones ---");
            ps.println("41. Suma y media de números");
            ps.println("42. Colegio y nacionalidades");
            ps.println("43. Días de la semana");
            ps.println("44. Conjunto de jugadores");
            ps.println("45. Bolas de dos colores");
            ps.println("\n0. Salir");

            // Para leer la opción, podemos usar el método de cualquiera de las dos clases,
            // ya que es un método de ayuda genérico. Usemos el de Ejercicio4.
            String opcion = ejerciciosPunto4.leerTexto("Elija una opción:");

            switch (opcion) {
                // --- Casos para el Punto 3 (llaman a los métodos de Ejercicio3) ---
                case "31": ejerciciosPunto3.resolverEjercicioA(); break;
                case "32": ejerciciosPunto3.resolverEjercicioB(); break;
                case "33": ejerciciosPunto3.resolverEjercicioC(); break;
                case "34": ejerciciosPunto3.resolverEjercicioD(); break;
                case "35": ejerciciosPunto3.resolverEjercicioE(); break;
                case "36": ejerciciosPunto3.resolverEjercicioF(); break;
                case "37": ejerciciosPunto3.resolverEjercicioG(); break;

                // --- Casos para el Punto 4 (llaman a los métodos de Ejercicio4) ---
                case "41": ejerciciosPunto4.resolverEjercicioA(); break;
                case "42": ejerciciosPunto4.resolverEjercicioB(); break;
                case "43": ejerciciosPunto4.resolverEjercicioC(); break;
                case "44": ejerciciosPunto4.resolverEjercicioD(); break;
                case "45": ejerciciosPunto4.resolverEjercicioE(); break;

                case "0":
                    ps.println("\n¡Hasta luego, campeón!");
                    return; // Termina el programa
                default:
                    ps.println("\n!!! Opción no válida. Por favor, elija un número del menú.");
            }

            // Pequeña pausa para que el usuario pueda leer la salida del ejercicio.
            ejerciciosPunto4.leerTexto("\nPresione ENTER para continuar...");
        }
    }
}
