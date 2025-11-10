package ar.edu.et32.chat.common;

/**
 * Tipo de mensaje intercambiado entre cliente y servidor.
 */
public enum MessageType {
    REGISTER,
    REGISTER_OK,
    REGISTER_ERROR,
    PUBLIC_MESSAGE,
    PRIVATE_MESSAGE,
    FILE_TRANSFER,
    COMMAND,
    COMMAND_RESULT,
    USER_LIST,
    SYSTEM,
    ERROR,
    LOGOUT
}
