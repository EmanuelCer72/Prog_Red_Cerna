package ar.edu.et32.chat.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Mensaje enviado desde el cliente hacia el servidor.
 */
public class ClientMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final MessageType type;
    private final String sender;
    private final String command;
    private final String target;
    private final Map<String, EncryptedPayload> payloads;
    private final String fileName;
    private final String publicKeyBase64;

    private ClientMessage(Builder builder) {
        this.type = builder.type;
        this.sender = builder.sender;
        this.command = builder.command;
        this.target = builder.target;
        this.payloads = builder.payloads;
        this.fileName = builder.fileName;
        this.publicKeyBase64 = builder.publicKeyBase64;
    }

    public MessageType getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getCommand() {
        return command;
    }

    public String getTarget() {
        return target;
    }

    public Map<String, EncryptedPayload> getPayloads() {
        return payloads;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPublicKeyBase64() {
        return publicKeyBase64;
    }

    public static Builder builder(MessageType type) {
        return new Builder(type);
    }

    public static class Builder {
        private final MessageType type;
        private String sender;
        private String command;
        private String target;
        private Map<String, EncryptedPayload> payloads;
        private String fileName;
        private String publicKeyBase64;

        private Builder(MessageType type) {
            this.type = type;
        }

        public Builder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public Builder command(String command) {
            this.command = command;
            return this;
        }

        public Builder target(String target) {
            this.target = target;
            return this;
        }

        public Builder payloads(Map<String, EncryptedPayload> payloads) {
            this.payloads = payloads;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder publicKeyBase64(String publicKeyBase64) {
            this.publicKeyBase64 = publicKeyBase64;
            return this;
        }

        public ClientMessage build() {
            return new ClientMessage(this);
        }
    }
}
