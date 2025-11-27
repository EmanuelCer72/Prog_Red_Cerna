package prueba;

import java.io.*;
import java.net.*;


public class client {
    public static void main(String[] args) {
        try {
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("ip: ");
            String ip = teclado.readLine();

            System.out.print("puerto: ");
            int puerto = Integer.parseInt(teclado.readLine());

            Socket socket = new Socket(ip, puerto);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Thread listener = new Thread(() -> {
                try {
                    String linea;
                    while ((linea = in.readLine()) != null) {
                        System.out.println(linea);
                    }
                } catch (Exception e) {
                    System.out.println("conexion cerrada");
                }
            });
            listener.start();

            while (true) {
                String msg = teclado.readLine();
                if (msg == null) break;

                out.println(msg);

                if (msg.equals("/logout")) break;
            }

            socket.close();

        } catch (Exception e) {
            System.out.println("no conecto al servidor");
        }
    }
}

//Código único otorgado por el servidor del profesor:BCA1948DBACF