package ar.edu.et32.chat.common;

import java.io.Serial;
import java.io.Serializable;

/**
 * Representa un usuario conectado junto con su clave pública.
 */
public class UserInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String username;
    private final String publicKeyBase64;

    public UserInfo(String username, String publicKeyBase64) {
        this.username = username;
        this.publicKeyBase64 = publicKeyBase64;
    }

    public String getUsername() {
        return username;
    }

    public String getPublicKeyBase64() {
        return publicKeyBase64;
    }
}
