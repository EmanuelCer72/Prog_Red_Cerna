import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE  = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED   = "\u001B[31m";

    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 4096;
    private static final String OUTPUT_DIR = "received_files";

    public static void main(String[] args) {
        System.out.println(ANSI_BLUE + "[INFO] Servidor en puerto " + PORT + ANSI_RESET);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println(ANSI_BLUE + "[INFO] Esperando conexión..." + ANSI_RESET);
            while (true) {
                try (Socket client = serverSocket.accept()) {
                    System.out.println(ANSI_GREEN + "[CONEXIÓN] Cliente conectado: " + client.getRemoteSocketAddress() + ANSI_RESET);
                    handleClient(client);
                } catch (IOException e) {
                    System.err.println(ANSI_RED + "[ERROR] Cliente: " + e.getMessage() + ANSI_RESET);
                }
            }
        } catch (IOException e) {
            System.err.println(ANSI_RED + "[FATAL] No se pudo iniciar: " + e.getMessage() + ANSI_RESET);
        }
    }

    private static void handleClient(Socket client) {
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) dir.mkdirs();

        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(client.getInputStream()))) {
            while (true) {
                int nameLen;
                try {
                    nameLen = dis.readInt();
                } catch (EOFException eof) {
                    System.out.println(ANSI_BLUE + "[INFO] Cliente desconectado." + ANSI_RESET);
                    break;
                }
                if (nameLen == 0) {
                    System.out.println(ANSI_BLUE + "[INFO] Fin de transmisión." + ANSI_RESET);
                    break;
                }
                byte[] nameBytes = new byte[nameLen];
                dis.readFully(nameBytes);
                String filename = new String(nameBytes, "UTF-8");
                long fileSize = dis.readLong();
                Path outPath = Path.of(OUTPUT_DIR, filename);
                outPath = resolveUniquePath(outPath);

                try (OutputStream fos = new BufferedOutputStream(Files.newOutputStream(outPath))) {
                    byte[] buffer = new byte[BUFFER_SIZE];
                    long remaining = fileSize;
                    while (remaining > 0) {
                        int read = dis.read(buffer, 0, (int)Math.min(buffer.length, remaining));
                        if (read == -1) throw new EOFException();
                        fos.write(buffer, 0, read);
                        remaining -= read;
                    }
                }
                System.out.println(ANSI_GREEN + "[OK] Archivo guardado: " + outPath.getFileName() + ANSI_RESET);
            }
        } catch (IOException e) {
            System.err.println(ANSI_RED + "[ERROR] " + e.getMessage() + ANSI_RESET);
        }
    }

    private static Path resolveUniquePath(Path base) {
        Path path = base;
        int counter = 1;
        while (Files.exists(path)) {
            String name = base.getFileName().toString();
            int dot = name.lastIndexOf('.');
            String nameOnly = (dot == -1) ? name : name.substring(0, dot);
            String ext = (dot == -1) ? "" : name.substring(dot);
            String newName = nameOnly + "(" + counter + ")" + ext;
            path = base.getParent().resolve(newName);
            counter++;
        }
        return path;
    }
}
