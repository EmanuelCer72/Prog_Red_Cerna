package prueba;

import java.io.*;
import java.net.*;
import java.util.*;

public class server {

    private static final String BIENVENIDA = "bienvenido al servidor local";
    private static List<ClientHandler> clientes = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket();
            System.out.println("servidor prendido, esperando clientes: ");

            while (true) {
                Socket socket = server.accept();
                ClientHandler handler = new ClientHandler(socket);
                clientes.add(handler);
                handler.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class ClientHandler extends Thread {
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        public ClientHandler(Socket s) throws Exception {
            this.socket = s;
            this.in = new DataInputStream(s.getInputStream());
            this.out = new DataOutputStream(s.getOutputStream());
        }

        @Override
        public void run() {
            try {
                out.writeUTF(BIENVENIDA);

                while (true) {
                    String msg = in.readUTF();

                    if (msg.equals("/hi")) {
                        out.writeUTF(BIENVENIDA);

                    } else if (msg.equals("/logout")) {
                        out.writeUTF("DESCONECTADO");
                        cerrarConexion();
                        break;

                    } else if (msg.startsWith("/")) {
                        out.writeUTF("COMANDO DESCONOCIDO");

                    } else {
                        enviarATodos(msg);
                        
                    }
                }

            } catch (Exception e) {
                cerrarConexion();
            }
        }

        private void enviarATodos(String mensaje) {
            synchronized (clientes) {
                for (ClientHandler cli : clientes) {
                    try {
                        cli.out.writeUTF(mensaje);
                    } catch (Exception ignored) {}
                }
            }
        }

        private void cerrarConexion() {
            try {
                clientes.remove(this);
                socket.close();
            } catch (Exception ignored) {}
        }
    }
}


//Código único otorgado por el servidor del profesor:BCA1948DBACF
