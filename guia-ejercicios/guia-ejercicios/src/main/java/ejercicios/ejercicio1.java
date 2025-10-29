package ejercicios;

import java.io.PrintStream;
import java.util.Scanner;

public class ejercicio1 {

    public static void main(String[] args) {
        PrintStream ps = new PrintStream(System.out);
        PrintStream psErr = new PrintStream(System.err);
        Scanner sc = new Scanner(System.in);

        calcularSueldo(ps, sc);
        calcularAnguloRestante(ps, sc);
        calcularPerimetroCuadrado(ps, sc);
        conversionTemperatura(ps, sc);
        conversionTiempo(ps, sc);
        calcularPlanes(ps, sc);
        mostrarMesNacimiento(ps, sc);

        sc.close();
    }

    // Ejercicio 1A
    public static void calcularSueldo(PrintStream ps, Scanner sc) {
        ps.print("Ingrese el precio de su hora de trabajo: ");
        int precioHora = sc.nextInt();

        ps.print("Ingrese cantidad de horas de trabajo: ");
        int horasTrabajadas = sc.nextInt();

        ps.println(horasTrabajadas * precioHora);
    }

    // Ejercicio 1B
    public static void calcularAnguloRestante(PrintStream ps, Scanner sc) {
        ps.print("Ingrese del primer angulo: ");
        float angulo1 = sc.nextFloat();

        ps.print("Ingrese del segundo angulo: ");
        float angulo2 = sc.nextFloat();

        float total = 180;
        ps.println(total - (angulo1 + angulo2));
    }

    // Ejercicio 1C
    public static void calcularPerimetroCuadrado(PrintStream ps, Scanner sc) {
        ps.print("Ingrese area del cuadrado: ");
        float area = sc.nextFloat();

        double perimetro = 4 * Math.sqrt(area);

        ps.println(perimetro);
    }

    // Ejercicio 1D
    public static void conversionTemperatura(PrintStream ps, Scanner sc) {
        ps.print("Ingrese temperatura en Fahrenheit: ");
        float tempFahrenheit = sc.nextFloat();

        float tempCelsius = (tempFahrenheit - 32) * (5.0f / 9.0f);

        ps.println(tempCelsius);
    }

    // Ejercicio 1E
    public static void conversionTiempo(PrintStream ps, Scanner sc) {
        ps.print("Ingrese el tiempo en segundos: ");
        float totalSegundos = sc.nextFloat();

        float dias = totalSegundos / 86400;
        float horas = (totalSegundos % 86400) / 3600;
        float minutos = (totalSegundos % 3600) / 60;
        float segundos = totalSegundos % 60;

        ps.println(dias + " días, " + horas + " horas, " + minutos + " minutos, " + segundos + " segundos.");
    }

    // Ejercicio 1F
    public static void calcularPlanes(PrintStream ps, Scanner sc) {
        ps.print("Ingrese el precio del artículo: ");
        float precio = sc.nextFloat();

        float precioPlan1 = precio - (precio * 0.10f);
        float precioPlan2 = precio + (precio * 0.10f);
        float cuotaPlan2 = precioPlan2 / 2;

        float precioPlan3 = precio + (precio * 0.15f);
        float cuotaPlan3 = (precioPlan3 * 0.75f) / 5;

        float precioPlan4 = precio + (precio * 0.25f);
        float cuotaPlan4PrimeraParte = (precioPlan4 * 0.60f) / 4;
        float cuotaPlan4SegundaParte = (precioPlan4 * 0.40f) / 4;

        ps.println("Plan 1: 100% al contado con un 10% de descuento.");
        ps.println("Precio final: $" + precioPlan1);

        ps.println("Plan 2: 50% al contado y el resto en 2 cuotas iguales con un 10% de incremento.");
        ps.println("Precio final: $" + precioPlan2);
        ps.println("Cuota 1 y 2: $" + cuotaPlan2);

        ps.println("Plan 3: 25% al contado y el resto en 5 cuotas iguales con un 15% de incremento.");
        ps.println("Precio final: $" + precioPlan3);
        ps.println("Cuotas 1 a 5: $" + cuotaPlan3);

        ps.println("Plan 4: Totalmente financiado en 8 cuotas con un 25% de incremento.");
        ps.println("Precio final: $" + precioPlan4);
        ps.println("Cuotas 1 a 4: $" + cuotaPlan4PrimeraParte);
        ps.println("Cuotas 5 a 8: $" + cuotaPlan4SegundaParte);
    }

    // Ejercicio 1G
    public static void mostrarMesNacimiento(PrintStream ps, Scanner sc) {
        ps.print("Ingrese su signo zodiacal: ");
        String signo = sc.next().toLowerCase();

        switch (signo) {
            case "aries":
                ps.println("Mes de nacimiento aproximado: Marzo - Abril");
                break;
            case "tauro":
                ps.println("Mes de nacimiento aproximado: Abril - Mayo");
                break;
            case "géminis":
                ps.println("Mes de nacimiento aproximado: Mayo - Junio");
                break;
            case "cáncer":
                ps.println("Mes de nacimiento aproximado: Junio - Julio");
                break;
            case "leo":
                ps.println("Mes de nacimiento aproximado: Julio - Agosto");
                break;
            case "virgo":
                ps.println("Mes de nacimiento aproximado: Agosto - Septiembre");
                break;
            case "libra":
                ps.println("Mes de nacimiento aproximado: Septiembre - Octubre");
                break;
            case "escorpio":
                ps.println("Mes de nacimiento aproximado: Octubre - Noviembre");
                break;
            case "sagitario":
                ps.println("Mes de nacimiento aproximado: Noviembre - Diciembre");
                break;
            case "capricornio":
                ps.println("Mes de nacimiento aproximado: Diciembre - Enero");
                break;
            case "acuario":
                ps.println("Mes de nacimiento aproximado: Enero - Febrero");
                break;
            case "piscis":
                ps.println("Mes de nacimiento aproximado: Febrero - Marzo");
                break;
            default:
                ps.println("Signo zodiacal no reconocido.");
                break;
        }
    }
}
