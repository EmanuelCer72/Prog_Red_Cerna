package ar.edu.et32.chat.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Mensaje enviado por el servidor hacia los clientes.
 */
public class ServerMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final MessageType type;
    private final String sender;
    private final EncryptedPayload payload;
    private final String infoMessage;
    private final String fileName;
    private final List<UserInfo> users;

    private ServerMessage(Builder builder) {
        this.type = builder.type;
        this.sender = builder.sender;
        this.payload = builder.payload;
        this.infoMessage = builder.infoMessage;
        this.fileName = builder.fileName;
        this.users = builder.users;
    }

    public MessageType getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public EncryptedPayload getPayload() {
        return payload;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public String getFileName() {
        return fileName;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public static Builder builder(MessageType type) {
        return new Builder(type);
    }

    public static class Builder {
        private final MessageType type;
        private String sender;
        private EncryptedPayload payload;
        private String infoMessage;
        private String fileName;
        private List<UserInfo> users;

        private Builder(MessageType type) {
            this.type = type;
        }

        public Builder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public Builder payload(EncryptedPayload payload) {
            this.payload = payload;
            return this;
        }

        public Builder infoMessage(String infoMessage) {
            this.infoMessage = infoMessage;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder users(List<UserInfo> users) {
            this.users = users;
            return this;
        }

        public ServerMessage build() {
            return new ServerMessage(this);
        }
    }
}
