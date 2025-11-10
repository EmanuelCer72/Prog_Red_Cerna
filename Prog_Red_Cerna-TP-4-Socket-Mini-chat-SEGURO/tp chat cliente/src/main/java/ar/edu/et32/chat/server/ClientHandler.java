package ar.edu.et32.chat.server;

import ar.edu.et32.chat.common.ClientMessage;
import ar.edu.et32.chat.common.MessageType;
import ar.edu.et32.chat.common.ServerMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Locale;

/**
 * Maneja la conexión de un cliente individual.
 */
class ClientHandler implements Runnable {

    private final ChatServer server;
    private final Socket socket;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean running = true;

    private String username;
    private String publicKeyBase64;

    ClientHandler(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());

            while (running) {
                Object object = input.readObject();
                if (!(object instanceof ClientMessage message)) {
                    sendError("Mensaje inválido recibido.");
                    continue;
                }
                handleMessage(message);
            }
        } catch (EOFException | SocketException e) {
            // cierre esperado por parte del cliente.
        } catch (Exception e) {
            System.err.printf("Error manejando cliente %s: %s%n", username, e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void handleMessage(ClientMessage message) {
        if (username == null) {
            if (message.getType() == MessageType.REGISTER) {
                handleRegister(message);
            } else {
                sendError("Debe registrarse antes de enviar mensajes.");
            }
            return;
        }

        if (!username.equals(message.getSender())) {
            sendError("Identidad del remitente no coincide.");
            return;
        }

        switch (message.getType()) {
            case PUBLIC_MESSAGE, PRIVATE_MESSAGE, FILE_TRANSFER -> server.forwardMessage(message);
            case COMMAND -> processCommand(message.getCommand());
            case LOGOUT -> requestLogout();
            default -> sendError("Tipo de mensaje no soportado.");
        }
    }

    private void handleRegister(ClientMessage message) {
        String requestedUsername = message.getSender();
        String receivedKey = message.getPublicKeyBase64();
        if (requestedUsername == null || requestedUsername.isBlank() || receivedKey == null) {
            send(ServerMessage.builder(MessageType.REGISTER_ERROR)
                    .infoMessage("Registro inválido: faltan datos.")
                    .build());
            running = false;
            return;
        }

        if (!server.usernameAvailable(requestedUsername)) {
            send(ServerMessage.builder(MessageType.REGISTER_ERROR)
                    .infoMessage("El usuario ya existe, elija otro nombre.")
                    .build());
            running = false;
            return;
        }

        this.username = requestedUsername;
        this.publicKeyBase64 = receivedKey;
        server.registerClient(username, this);
        send(ServerMessage.builder(MessageType.REGISTER_OK)
                .infoMessage("Conexión exitosa al servidor.")
                .build());
        server.sendUserList(this);
    }

    private void processCommand(String command) {
        if (command == null) {
            sendError("Comando vacío.");
            return;
        }
        String normalized = command.trim().toLowerCase(Locale.ROOT);
        switch (normalized) {
            case "/listar" -> server.sendUserList(this);
            case "/vercomandos" -> send(ServerMessage.builder(MessageType.COMMAND_RESULT)
                    .infoMessage("Comandos disponibles: /salir, /listar, /verComandos, /msg [usuario] [mensaje], /enviarArchivo [usuario] [archivo], /ayuda")
                    .build());
            case "/ayuda" -> send(ServerMessage.builder(MessageType.COMMAND_RESULT)
                    .infoMessage("Use mensajes sin prefijo para hablar con todos. Para privados: /msg [usuario] [mensaje]. Para archivos: /enviarArchivo [usuario] [archivo].")
                    .build());
            case "/salir" -> requestLogout();
            default -> send(ServerMessage.builder(MessageType.ERROR)
                    .infoMessage("Comando no válido, intente /verComandos.")
                    .build());
        }
    }

    private void requestLogout() {
        send(ServerMessage.builder(MessageType.LOGOUT)
                .infoMessage("Sesión finalizada.")
                .build());
        running = false;
    }

    void send(ServerMessage message) {
        try {
            synchronized (output) {
                output.writeObject(message);
                output.flush();
            }
        } catch (IOException e) {
            System.err.printf("No se pudo enviar mensaje a %s: %s%n", username, e.getMessage());
        }
    }

    void sendInfo(String text) {
        send(ServerMessage.builder(MessageType.SYSTEM)
                .infoMessage(text)
                .build());
    }

    void sendError(String text) {
        send(ServerMessage.builder(MessageType.ERROR)
                .infoMessage(text)
                .build());
    }

    private void closeConnection() {
        running = false;
        server.handleClientDisconnect(username, this);
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
            socket.close();
        } catch (IOException ignored) {
        }
    }

    String getUsername() {
        return username;
    }

    String getPublicKeyBase64() {
        return publicKeyBase64;
    }
}
