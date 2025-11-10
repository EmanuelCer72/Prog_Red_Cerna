package ar.edu.et32.chat.server;

import ar.edu.et32.chat.common.ClientMessage;
import ar.edu.et32.chat.common.MessageType;
import ar.edu.et32.chat.common.ServerMessage;
import ar.edu.et32.chat.common.UserInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Servidor de chat seguro que oficia de router entre clientes.
 */
public class ChatServer {

    private final int port;
    private final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.printf("Servidor escuchando en el puerto %d%n", port);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(this, socket);
                Thread thread = new Thread(handler, "ClientHandler-" + socket.getRemoteSocketAddress());
                thread.start();
            }
        }
    }

    void registerClient(String username, ClientHandler handler) {
        clients.put(username, handler);
        broadcastUserList();
        broadcastSystemMessage(String.format("%s se ha conectado.", username));
    }

    boolean usernameAvailable(String username) {
        return !clients.containsKey(username);
    }

    void handleClientDisconnect(String username, ClientHandler handler) {
        if (username != null && clients.remove(username, handler)) {
            broadcastUserList();
            broadcastSystemMessage(String.format("%s se ha desconectado.", username));
        }
    }

    void forwardMessage(ClientMessage message) {
        if (message.getPayloads() == null || message.getPayloads().isEmpty()) {
            ClientHandler origin = clients.get(message.getSender());
            if (origin != null) {
                origin.sendInfo("No se encontraron destinatarios para el mensaje.");
            }
            return;
        }

        message.getPayloads().forEach((recipient, payload) -> {
            ClientHandler target = clients.get(recipient);
            if (target == null) {
                ClientHandler origin = clients.get(message.getSender());
                if (origin != null) {
                    origin.sendError(String.format("Usuario %s no encontrado.", recipient));
                }
                return;
            }
            MessageType type = Objects.requireNonNullElse(message.getType(), MessageType.PUBLIC_MESSAGE);
            ServerMessage serverMessage = ServerMessage.builder(type)
                    .sender(message.getSender())
                    .payload(payload)
                    .fileName(message.getFileName())
                    .build();
            target.send(serverMessage);
        });
    }

    void sendUserList(ClientHandler target) {
        List<UserInfo> users = buildUserInfo();
        ServerMessage message = ServerMessage.builder(MessageType.USER_LIST)
                .users(users)
                .infoMessage("Usuarios conectados: " + users.size())
                .build();
        target.send(message);
    }

    void broadcastUserList() {
        List<UserInfo> users = buildUserInfo();
        ServerMessage message = ServerMessage.builder(MessageType.USER_LIST)
                .users(users)
                .build();
        clients.values().forEach(handler -> handler.send(message));
    }

    private List<UserInfo> buildUserInfo() {
        List<UserInfo> users = new ArrayList<>();
        clients.values().forEach(handler -> {
            if (handler.getUsername() != null) {
                users.add(new UserInfo(handler.getUsername(), handler.getPublicKeyBase64()));
            }
        });
        return users;
    }

    void broadcastSystemMessage(String message) {
        ServerMessage serverMessage = ServerMessage.builder(MessageType.SYSTEM)
                .infoMessage(message)
                .build();
        clients.values().forEach(handler -> handler.send(serverMessage));
    }

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 5000;
        new ChatServer(port).start();
    }
}
