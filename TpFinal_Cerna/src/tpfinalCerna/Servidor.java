package tpfinalCerna;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Servidor {

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket j1 = null;
        Socket j2 = null;

        try {
            server = new ServerSocket(5000);
            System.out.println("Esperando jugadores...");

            j1 = server.accept();
            System.out.println("Jugador 1 conectado");
            DataInputStream in1 = new DataInputStream(j1.getInputStream());
            DataOutputStream out1 = new DataOutputStream(j1.getOutputStream());
            out1.writeUTF("X");

            j2 = server.accept();
            System.out.println("Jugador 2 conectado");
            DataInputStream in2 = new DataInputStream(j2.getInputStream());
            DataOutputStream out2 = new DataOutputStream(j2.getOutputStream());
            out2.writeUTF("O");

            Tablero tab = new Tablero();

            boolean fin = false;
            int turno = 1;

            while (!fin) {
                DataInputStream inActual;
                DataOutputStream outActual;
                String simbolo;

                if (turno == 1) {
                    out1.writeUTF("TU");
                    out2.writeUTF("NO");
                    inActual = in1;
                    outActual = out1;
                    simbolo = "X";
                } else {
                    out2.writeUTF("TU");
                    out1.writeUTF("NO");
                    inActual = in2;
                    outActual = out2;
                    simbolo = "O";
                }

                outActual.writeUTF(tab.mostrar());

                boolean movValido = false;

                while (!movValido) {
                    int f = inActual.readInt();
                    int c = inActual.readInt();
                    movValido = tab.colocarSimbolo(f, c, simbolo);
                    outActual.writeBoolean(movValido);
                }

                if (tab.esGanador(simbolo)) {
                    if (simbolo.equals("X")) {
                        out1.writeUTF("GANASTE");
                        out2.writeUTF("PERDISTE");
                    } else {
                        out2.writeUTF("GANASTE");
                        out1.writeUTF("PERDISTE");
                    }
                    fin = true;
                } else if (tab.esEmpate()) {
                    out1.writeUTF("EMPATE");
                    out2.writeUTF("EMPATE");
                    fin = true;
                } else {
                    turno = (turno == 1) ? 2 : 1;
                }
            }

        } catch (Exception e) {
            System.out.println("Error en servidor: " + e.getMessage());
        } finally {
            try {
                if (j1 != null) j1.close();
                if (j2 != null) j2.close();
                if (server != null) server.close();
            } catch (Exception x) {}
        }
    }
}
