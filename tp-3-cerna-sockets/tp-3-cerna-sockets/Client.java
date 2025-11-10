import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class Client {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE  = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED   = "\u001B[31m";

    private static final int BUFFER_SIZE = 4096;
    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {

            System.out.println(ANSI_GREEN + "[OK] Conectado a " + HOST + ":" + PORT + ANSI_RESET);
            boolean seguir = true;

            while (seguir) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Seleccionar archivo");
                int res = chooser.showOpenDialog(null);
                if (res != JFileChooser.APPROVE_OPTION) {
                    dos.writeInt(0);
                    dos.flush();
                    break;
                }

                File file = chooser.getSelectedFile();
                String filename = file.getName();
                long fileSize = Files.size(file.toPath());
                byte[] nameBytes = filename.getBytes("UTF-8");

                try (InputStream fis = new BufferedInputStream(new FileInputStream(file))) {
                    dos.writeInt(nameBytes.length);
                    dos.write(nameBytes);
                    dos.writeLong(fileSize);

                    byte[] buffer = new byte[BUFFER_SIZE];
                    int read;
                    while ((read = fis.read(buffer)) != -1) {
                        dos.write(buffer, 0, read);
                    }
                    dos.flush();
                    System.out.println(ANSI_GREEN + "[OK] Archivo enviado: " + filename + ANSI_RESET);
                }

                int resp = JOptionPane.showConfirmDialog(null, "¿Enviar otro archivo?", "Continuar", JOptionPane.YES_NO_OPTION);
                if (resp != JOptionPane.YES_OPTION) {
                    dos.writeInt(0);
                    dos.flush();
                    seguir = false;
                    System.out.println(ANSI_BLUE + "[INFO] Fin de envío." + ANSI_RESET);
                }
            }
        } catch (IOException e) {
            System.err.println(ANSI_RED + "[ERROR] " + e.getMessage() + ANSI_RESET);
        }
    }
}
