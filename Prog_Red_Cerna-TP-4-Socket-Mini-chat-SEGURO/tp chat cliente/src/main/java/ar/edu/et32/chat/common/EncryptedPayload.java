package ar.edu.et32.chat.common;

import java.io.Serial;
import java.io.Serializable;

/**
 * Contiene los datos cifrados enviados entre clientes.
 */
public class EncryptedPayload implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String encryptedKeyBase64;
    private final String ivBase64;
    private final String cipherTextBase64;

    public EncryptedPayload(String encryptedKeyBase64, String ivBase64, String cipherTextBase64) {
        this.encryptedKeyBase64 = encryptedKeyBase64;
        this.ivBase64 = ivBase64;
        this.cipherTextBase64 = cipherTextBase64;
    }

    public String getEncryptedKeyBase64() {
        return encryptedKeyBase64;
    }

    public String getIvBase64() {
        return ivBase64;
    }

    public String getCipherTextBase64() {
        return cipherTextBase64;
    }
}
