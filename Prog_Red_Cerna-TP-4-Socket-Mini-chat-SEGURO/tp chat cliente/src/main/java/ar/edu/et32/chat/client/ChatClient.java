package ar.edu.et32.chat.client;

import ar.edu.et32.chat.common.ClientMessage;
import ar.edu.et32.chat.common.CryptoUtils;
import ar.edu.et32.chat.common.EncryptedPayload;
import ar.edu.et32.chat.common.MessageType;
import ar.edu.et32.chat.common.ServerMessage;
import ar.edu.et32.chat.common.UserInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Cliente CLI que cifra mensajes punto a punto y se comunica con el servidor.
 */
public class ChatClient {

    private final String host;
    private final int port;
    private final String username;

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private final Map<String, PublicKey> userKeys = new ConcurrentHashMap<>();
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private final CountDownLatch registrationLatch = new CountDownLatch(1);
    private volatile boolean running = true;

    public ChatClient(String host, int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
    }

    public void start() throws Exception {
        connect();
        listenServerAsync();
        register();
        registrationLatch.await();
        if (!running) {
            close();
            return;
        }
        runConsoleLoop();
    }

    private void connect() throws IOException, GeneralSecurityException {
        socket = new Socket(host, port);
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();
        input = new ObjectInputStream(socket.getInputStream());

        KeyPair keyPair = CryptoUtils.generateRsaKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    private void register() {
        String publicKeyBase64 = CryptoUtils.publicKeyToBase64(publicKey);
        ClientMessage registerMessage = ClientMessage.builder(MessageType.REGISTER)
                .sender(username)
                .publicKeyBase64(publicKeyBase64)
                .build();
        send(registerMessage);
    }

    private void listenServerAsync() {
        Thread listener = new Thread(new ServerListener(), "ServerListener");
        listener.setDaemon(true);
        listener.start();
    }

    private void runConsoleLoop() {
        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            printInfo("Escriba un mensaje para chatear con todos o /verComandos para ayuda.");
            while (running) {
                String line = console.readLine();
                if (line == null) {
                    break;
                }
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.startsWith("/")) {
                    handleCommand(line);
                } else {
                    sendBroadcast(line);
                }
            }
        } catch (IOException e) {
            printError("Error leyendo desde consola: " + e.getMessage());
        } finally {
            close();
        }
    }

    private void handleCommand(String commandLine) {
        if (commandLine.equalsIgnoreCase("/salir")) {
            sendCommand(commandLine);
            running = false;
            return;
        }

        if (commandLine.equalsIgnoreCase("/listar") ||
                commandLine.equalsIgnoreCase("/vercomandos") ||
                commandLine.equalsIgnoreCase("/ayuda")) {
            sendCommand(commandLine);
            return;
        }

        if (commandLine.startsWith("/msg ")) {
            String[] parts = commandLine.split(" ", 3);
            if (parts.length < 3) {
                printError("Uso: /msg [usuario] [mensaje]");
                return;
            }
            sendPrivate(parts[1], parts[2]);
            return;
        }

        if (commandLine.startsWith("/enviarArchivo ")) {
            String[] parts = commandLine.split(" ", 3);
            if (parts.length < 3) {
                printError("Uso: /enviarArchivo [usuario] [archivo]");
                return;
            }
            sendFile(parts[1], parts[2]);
            return;
        }

        printInfo("Comando no válido, intente /verComandos.");
    }

    private void sendCommand(String command) {
        ClientMessage message = ClientMessage.builder(MessageType.COMMAND)
                .sender(username)
                .command(command)
                .build();
        send(message);
    }

    private void sendBroadcast(String text) {
        Map<String, EncryptedPayload> payloads = buildPayloadForAll(text.getBytes());
        if (payloads.isEmpty()) {
            printInfo("No hay otros usuarios conectados.");
            return;
        }
        ClientMessage message = ClientMessage.builder(MessageType.PUBLIC_MESSAGE)
                .sender(username)
                .payloads(payloads)
                .build();
        send(message);
        printClient(String.format("(yo) %s", text));
    }

    private void sendPrivate(String target, String text) {
        PublicKey key = userKeys.get(target);
        if (key == null) {
            printError("Usuario no encontrado para el mensaje privado.");
            return;
        }
        Map<String, EncryptedPayload> payloads = new HashMap<>();
        try {
            payloads.put(target, CryptoUtils.encrypt(text.getBytes(), key));
        } catch (GeneralSecurityException e) {
            printError("No se pudo cifrar el mensaje: " + e.getMessage());
            return;
        }
        ClientMessage message = ClientMessage.builder(MessageType.PRIVATE_MESSAGE)
                .sender(username)
                .target(target)
                .payloads(payloads)
                .build();
        send(message);
        printClient(String.format("(privado a %s) %s", target, text));
    }

    private void sendFile(String target, String filePath) {
        PublicKey key = userKeys.get(target);
        if (key == null) {
            printError("Usuario no encontrado para enviar archivo.");
            return;
        }
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            printError("Archivo no encontrado: " + filePath);
            return;
        }
        try {
            byte[] data = Files.readAllBytes(path);
            Map<String, EncryptedPayload> payloads = new HashMap<>();
            payloads.put(target, CryptoUtils.encrypt(data, key));
            ClientMessage message = ClientMessage.builder(MessageType.FILE_TRANSFER)
                    .sender(username)
                    .target(target)
                    .fileName(path.getFileName().toString())
                    .payloads(payloads)
                    .build();
            send(message);
            printSuccess("Archivo enviado correctamente.");
        } catch (IOException e) {
            printError("No se pudo leer el archivo: " + e.getMessage());
        } catch (GeneralSecurityException e) {
            printError("No se pudo cifrar el archivo: " + e.getMessage());
        }
    }

    private Map<String, EncryptedPayload> buildPayloadForAll(byte[] data) {
        Map<String, EncryptedPayload> payloads = new HashMap<>();
        userKeys.forEach((user, key) -> {
            try {
                payloads.put(user, CryptoUtils.encrypt(data, key));
            } catch (GeneralSecurityException e) {
                printError("No se pudo cifrar mensaje para " + user + ": " + e.getMessage());
            }
        });
        return payloads;
    }

    private void updateUserKeys(List<UserInfo> users) {
        Map<String, PublicKey> updated = new HashMap<>();
        for (UserInfo user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                continue;
            }
            try {
                updated.put(user.getUsername(), CryptoUtils.publicKeyFromBase64(user.getPublicKeyBase64()));
            } catch (GeneralSecurityException e) {
                printError("No se pudo importar clave pública de " + user.getUsername() + ": " + e.getMessage());
            }
        }
        userKeys.clear();
        userKeys.putAll(updated);
    }

    private void send(ClientMessage message) {
        try {
            synchronized (output) {
                output.writeObject(message);
                output.flush();
            }
        } catch (IOException e) {
            printError("No se pudo enviar datos al servidor: " + e.getMessage());
        }
    }

    private void close() {
        running = false;
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ignored) {
        }
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException ignored) {
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ignored) {
        }
    }

    private void printSuccess(String text) {
        System.out.println(ConsoleColors.SUCCESS + text + ConsoleColors.RESET);
    }

    private void printClient(String text) {
        System.out.println(ConsoleColors.CLIENT + text + ConsoleColors.RESET);
    }

    private void printError(String text) {
        System.out.println(ConsoleColors.ERROR + text + ConsoleColors.RESET);
    }

    private void printInfo(String text) {
        System.out.println(ConsoleColors.INFO + text + ConsoleColors.RESET);
    }

    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                while (running) {
                    Object object = input.readObject();
                    if (!(object instanceof ServerMessage message)) {
                        printError("Mensaje desconocido recibido del servidor.");
                        continue;
                    }
                    handleServerMessage(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                if (running) {
                    printError("Conexión cerrada: " + e.getMessage());
                }
            } finally {
                running = false;
            }
        }

        private void handleServerMessage(ServerMessage message) {
            switch (message.getType()) {
                case REGISTER_OK -> {
                    if (message.getInfoMessage() != null) {
                        printSuccess(message.getInfoMessage());
                    }
                    registrationLatch.countDown();
                }
                case REGISTER_ERROR -> {
                    printError(message.getInfoMessage());
                    registrationLatch.countDown();
                    running = false;
                }
                case USER_LIST -> {
                    List<UserInfo> users = message.getUsers();
                    if (users != null) {
                        updateUserKeys(users);
                    }
                    if (message.getInfoMessage() != null) {
                        printInfo(message.getInfoMessage());
                    }
                }
                case SYSTEM -> {
                    if (message.getInfoMessage() != null) {
                        printSuccess(message.getInfoMessage());
                    }
                }
                case COMMAND_RESULT -> {
                    if (message.getInfoMessage() != null) {
                        printInfo(message.getInfoMessage());
                    }
                }
                case ERROR -> {
                    if (message.getInfoMessage() != null) {
                        printError(message.getInfoMessage());
                    }
                }
                case PUBLIC_MESSAGE -> showIncomingMessage(message, false);
                case PRIVATE_MESSAGE -> showIncomingMessage(message, true);
                case FILE_TRANSFER -> handleIncomingFile(message);
                case LOGOUT -> {
                    if (message.getInfoMessage() != null) {
                        printInfo(message.getInfoMessage());
                    }
                    running = false;
                }
                default -> printInfo("Mensaje del servidor sin manejar: " + message.getType());
            }
        }

        private void showIncomingMessage(ServerMessage message, boolean privateMsg) {
            EncryptedPayload payload = message.getPayload();
            if (payload == null) {
                printError("Mensaje recibido sin contenido cifrado.");
                return;
            }
            try {
                byte[] plaintext = CryptoUtils.decrypt(payload, privateKey);
                String text = new String(plaintext);
                if (privateMsg) {
                    printInfo(String.format("(privado de %s) %s", message.getSender(), text));
                } else {
                    printInfo(String.format("%s: %s", message.getSender(), text));
                }
            } catch (GeneralSecurityException e) {
                printError("No se pudo descifrar el mensaje: " + e.getMessage());
            }
        }

        private void handleIncomingFile(ServerMessage message) {
            EncryptedPayload payload = message.getPayload();
            if (payload == null) {
                printError("Archivo recibido sin contenido.");
                return;
            }
            try {
                byte[] fileBytes = CryptoUtils.decrypt(payload, privateKey);
                String originalName = message.getFileName() != null ? message.getFileName() : "archivo.bin";
                Path downloadDir = Paths.get("downloads");
                Files.createDirectories(downloadDir);
                String sanitized = originalName.replaceAll("[^A-Za-z0-9._-]", "_");
                String filename = System.currentTimeMillis() + "_" + sanitized;
                Path destination = downloadDir.resolve(filename);
                Files.write(destination, fileBytes);
                printSuccess(String.format("Archivo '%s' recibido de %s.", filename, message.getSender()));
            } catch (GeneralSecurityException e) {
                printError("No se pudo descifrar el archivo: " + e.getMessage());
            } catch (IOException e) {
                printError("No se pudo guardar el archivo: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Uso: java ChatClient <usuario> [host] [puerto]");
            return;
        }
        String username = args[0];
        String host = args.length > 1 ? args[1] : "127.0.0.1";
        int port = args.length > 2 ? Integer.parseInt(args[2]) : 5000;
        new ChatClient(host, port, username).start();
    }
}
