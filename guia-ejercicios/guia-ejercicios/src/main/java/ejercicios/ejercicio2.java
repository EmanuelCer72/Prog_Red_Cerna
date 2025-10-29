package ejercicios;

import java.io.*;

public class ejercicio2 {

    public static void main(String[] args) throws IOException {
        PrintStream ps = new PrintStream(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        ordenarApellidos(ps, br);
        encontrarMenorNumero(ps, br);
        esParOImpar(ps, br);
        esDivisible(ps, br);
        mostrarSignoZodiacal(ps, br);
        apellidoMasLargo(ps, br);
        tablaMultiplicar(ps, br);
        esPrimo(ps, br);
    }

    // a) Ordenar tres apellidos alfabéticamente
    public static void ordenarApellidos(PrintStream ps, BufferedReader br) throws IOException {
        ps.println("Ingrese el primer apellido:");
        String a1 = br.readLine();
        ps.println("Ingrese el segundo apellido:");
        String a2 = br.readLine();
        ps.println("Ingrese el tercer apellido:");
        String a3 = br.readLine();

        String[] apellidos = { a1, a2, a3 };
        java.util.Arrays.sort(apellidos);

        ps.println("Apellidos ordenados:");
        for (String apellido : apellidos) {
            ps.println(apellido);
        }
    }

    // b) Menor de cuatro números reales
    public static void encontrarMenorNumero(PrintStream ps, BufferedReader br) throws IOException {
        ps.println("Ingrese cuatro números reales:");
        float n1 = Float.parseFloat(br.readLine());
        float n2 = Float.parseFloat(br.readLine());
        float n3 = Float.parseFloat(br.readLine());
        float n4 = Float.parseFloat(br.readLine());

        float menor = Math.min(Math.min(n1, n2), Math.min(n3, n4));
        ps.println("El menor número es: " + menor);
    }

    // c) Par o impar
    public static void esParOImpar(PrintStream ps, BufferedReader br) throws IOException {
        ps.print("Ingrese un número entero: ");
        int n = Integer.parseInt(br.readLine());

        if (n % 2 == 0) {
            ps.println("Es par.");
        } else {
            ps.println("Es impar.");
        }
    }

    // d) Divisible
    public static void esDivisible(PrintStream ps, BufferedReader br) throws IOException {
        ps.print("Ingrese el primer número real: ");
        float a = Float.parseFloat(br.readLine());
        ps.print("Ingrese el segundo número real: ");
        float b = Float.parseFloat(br.readLine());

        float mayor = Math.max(a, b);
        float menor = Math.min(a, b);

        if (menor == 0) {
            ps.println("No se puede dividir por cero.");
        } else if (mayor % menor == 0) {
            ps.println("El mayor es divisible por el menor.");
        } else {
            ps.println("El mayor NO es divisible por el menor.");
        }
    }

    // e) Signo zodiacal (con mes y día)
    public static void mostrarSignoZodiacal(PrintStream ps, BufferedReader br) throws IOException {
        ps.print("Ingrese el día de nacimiento: ");
        int dia = Integer.parseInt(br.readLine());
        ps.print("Ingrese el mes de nacimiento (número): ");
        int mes = Integer.parseInt(br.readLine());

        String signo;
        if ((mes == 3 && dia >= 21) || (mes == 4 && dia <= 19))
            signo = "Aries";
        else if ((mes == 4 && dia >= 20) || (mes == 5 && dia <= 20))
            signo = "Tauro";
        else if ((mes == 5 && dia >= 21) || (mes == 6 && dia <= 20))
            signo = "Géminis";
        else if ((mes == 6 && dia >= 21) || (mes == 7 && dia <= 22))
            signo = "Cáncer";
        else if ((mes == 7 && dia >= 23) || (mes == 8 && dia <= 22))
            signo = "Leo";
        else if ((mes == 8 && dia >= 23) || (mes == 9 && dia <= 22))
            signo = "Virgo";
        else if ((mes == 9 && dia >= 23) || (mes == 10 && dia <= 22))
            signo = "Libra";
        else if ((mes == 10 && dia >= 23) || (mes == 11 && dia <= 21))
            signo = "Escorpio";
        else if ((mes == 11 && dia >= 22) || (mes == 12 && dia <= 21))
            signo = "Sagitario";
        else if ((mes == 12 && dia >= 22) || (mes == 1 && dia <= 19))
            signo = "Capricornio";
        else if ((mes == 1 && dia >= 20) || (mes == 2 && dia <= 18))
            signo = "Acuario";
        else
            signo = "Piscis";

        ps.println("Su signo zodiacal es: " + signo);
    }

    // f) Apellido más largo
    public static void apellidoMasLargo(PrintStream ps, BufferedReader br) throws IOException {
        ps.print("Ingrese el nombre y apellido de la primera persona: ");
        String p1 = br.readLine();
        ps.print("Ingrese el nombre y apellido de la segunda persona: ");
        String p2 = br.readLine();

        String apellido1 = p1.substring(p1.lastIndexOf(" ") + 1);
        String apellido2 = p2.substring(p2.lastIndexOf(" ") + 1);

        if (apellido1.length() > apellido2.length()) {
            ps.println("El apellido más largo es: " + apellido1);
        } else if (apellido2.length() > apellido1.length()) {
            ps.println("El apellido más largo es: " + apellido2);
        } else {
            ps.println("Ambos apellidos tienen la misma longitud.");
        }
    }

    // g) Tabla de multiplicar
    public static void tablaMultiplicar(PrintStream ps, BufferedReader br) throws IOException {
        ps.print("Ingrese un número natural: ");
        int n = Integer.parseInt(br.readLine());

        ps.println("Tabla del " + n + ":");
        for (int i = 1; i <= 10; i++) {
            ps.println(n + " x " + i + " = " + (n * i));
        }
    }

    // h) Es primo
    public static void esPrimo(PrintStream ps, BufferedReader br) throws IOException {
        ps.print("Ingrese un número natural: ");
        int n = Integer.parseInt(br.readLine());

        if (n <= 1) {
            ps.println("No es primo.");
            return;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                ps.println("No es primo.");
                return;
            }
        }
        ps.println("Es primo.");
    }
}
