package tpfinalCerna;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Cliente {

    public static int leerNumero() throws Exception {
        int ch = System.in.read();
        while (ch == '\n' || ch == '\r') {
            ch = System.in.read();
        }
        return ch - '0';
    }

    public static void main(String[] args) {
        Socket socket = null;

        try {
            socket = new Socket("localhost", 5000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String simbolo = in.readUTF();
            System.out.println("Tu simbolo es: " + simbolo);

            boolean jugando = true;

            while (jugando) {
                String turno = in.readUTF();

                if (turno.equals("TU")) {
                    String tablero = in.readUTF();
                    System.out.println("Tablero:");
                    System.out.println(tablero);

                    System.out.print("Fila (0-2): ");
                    int f = leerNumero();
                    System.out.print("Columna (0-2): ");
                    int c = leerNumero();

                    out.writeInt(f);
                    out.writeInt(c);

                    boolean ok = in.readBoolean();
                    if (!ok) {
                        System.out.println("Movimiento invalido");
                    }

                } else if (turno.equals("NO")) {
                    System.out.println("Esperando al otro jugador...");
                } else if (turno.equals("GANASTE") || turno.equals("PERDISTE") || turno.equals("EMPATE")) {
                    System.out.println(turno);
                    jugando = false;
                }
            }

        } catch (Exception e) {
            System.out.println("Error cliente: " + e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (Exception x) {}
        }
    }
}
