import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 5001;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                String serverMsg = in.readLine();
                if (serverMsg == null) break;
                if (serverMsg.startsWith("REQUEST_NAME")) {
                    System.out.print("Ingrese su nombre: ");
                    String nombre = sc.nextLine();
                    out.println("NAME|"+nombre);
                } else if (serverMsg.startsWith("ROLE|")) {
                    System.out.println("Te asignaron: " + serverMsg.split("\|",2)[1]);
                } else if (serverMsg.startsWith("START|")) {
                    System.out.println("Comienza partida contra: " + serverMsg.split("\|",2)[1]);
                } else if (serverMsg.startsWith("BOARD|")) {
                    String payload = serverMsg.substring(6).replaceAll("\\n","\n");
                    System.out.println(payload);
                } else if (serverMsg.startsWith("YOUR_TURN")) {
                    System.out.print("Tu movimiento (fila col): ");
                    String line = sc.nextLine().trim();
                    String[] tokens = line.split("\s+");
                    if (tokens.length>=2) {
                        out.println("MOVE|"+tokens[0]+"|"+tokens[1]);
                    } else {
                        System.out.println("Entrada inválida");
                        out.println("MOVE|0|0");
                    }
                } else if (serverMsg.startsWith("WAIT")) {
                    System.out.println("Esperando jugada del oponente...");
                } else if (serverMsg.startsWith("INVALID")) {
                    System.out.println("Movimiento inválido, intentá de nuevo.");
                } else if (serverMsg.startsWith("RESULT|")) {
                    String res = serverMsg.split("\|",2)[1];
                    if ("WIN".equals(res)) System.out.println("Ganaste!");
                    else if ("LOSE".equals(res)) System.out.println("Perdiste.");
                    else if ("DRAW".equals(res)) System.out.println("Empate.");
                    break;
                } else if (serverMsg.startsWith("END|")) {
                    System.out.println("Partida finalizada: " + serverMsg.split("\|",2)[1]);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }
}
