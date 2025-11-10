import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 5001;
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private static final BlockingQueue<Socket> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        System.out.println("[INFO] Servidor iniciado en puerto " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            pool.submit(() -> pairMaker());
            while (true) {
                Socket client = serverSocket.accept();
                queue.put(client);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("[ERROR] " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }

    private static void pairMaker() {
        while (true) {
            try {
                Socket s1 = queue.take();
                Socket s2 = queue.take();
                pool.submit(new GameSession(s1, s2));
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

class GameSession implements Runnable {
    private Socket s1;
    private Socket s2;
    private Tablero tablero = new Tablero();

    public GameSession(Socket a, Socket b) {
        s1 = a; s2 = b;
    }

    public void run() {
        try (BufferedReader in1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
             PrintWriter out1 = new PrintWriter(s1.getOutputStream(), true);
             BufferedReader in2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
             PrintWriter out2 = new PrintWriter(s2.getOutputStream(), true)) {

            out1.println("ROLE|X");
            out2.println("ROLE|O");

            String name1 = requestName(in1, out1, "Player1");
            String name2 = requestName(in2, out2, "Player2");

            out1.println("START|" + name2);
            out2.println("START|" + name1);

            String current = "X";
            boolean running = true;
            while (running) {
                sendBoard(out1, out2);
                if ("X".equals(current)) {
                    out1.println("YOUR_TURN");
                    out2.println("WAIT");
                    String move = in1.readLine();
                    if (move == null) { out2.println("END|DISCONNECT"); break; }
                    if (processMove(move, "X", out1)) {
                        if (tablero.esGanador("X")) {
                            sendBoard(out1,out2);
                            out1.println("RESULT|WIN");
                            out2.println("RESULT|LOSE");
                            break;
                        } else if (tablero.esEmpate()) {
                            sendBoard(out1,out2);
                            out1.println("RESULT|DRAW");
                            out2.println("RESULT|DRAW");
                            break;
                        }
                        current = "O";
                    } else {
                        out1.println("INVALID");
                    }
                } else {
                    out2.println("YOUR_TURN");
                    out1.println("WAIT");
                    String move = in2.readLine();
                    if (move == null) { out1.println("END|DISCONNECT"); break; }
                    if (processMove(move, "O", out2)) {
                        if (tablero.esGanador("O")) {
                            sendBoard(out1,out2);
                            out2.println("RESULT|WIN");
                            out1.println("RESULT|LOSE");
                            break;
                        } else if (tablero.esEmpate()) {
                            sendBoard(out1,out2);
                            out1.println("RESULT|DRAW");
                            out2.println("RESULT|DRAW");
                            break;
                        }
                        current = "X";
                    } else {
                        out2.println("INVALID");
                    }
                }
            }
        } catch (IOException e) {
            // handle
        } finally {
            try { s1.close(); } catch (IOException e) {}
            try { s2.close(); } catch (IOException e) {}
        }
    }

    private String requestName(BufferedReader in, PrintWriter out, String fallback) throws IOException {
        out.println("REQUEST_NAME");
        String line = in.readLine();
        if (line != null && line.startsWith("NAME|")) return line.substring(5);
        return fallback;
    }

    private boolean processMove(String move, String simbolo, PrintWriter out) {
        if (move == null) return false;
        if (!move.startsWith("MOVE|")) return false;
        String[] parts = move.split("\|");
        if (parts.length < 3) return false;
        try {
            int r = Integer.parseInt(parts[1]) - 1;
            int c = Integer.parseInt(parts[2]) - 1;
            boolean ok = tablero.colocarSimbolo(r,c,simbolo);
            return ok;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void sendBoard(PrintWriter out1, PrintWriter out2) {
        String b = tablero.mostrarTablero();
        out1.println("BOARD|"+b.replaceAll("\n","\\n"));
        out2.println("BOARD|"+b.replaceAll("\n","\\n"));
    }
}
