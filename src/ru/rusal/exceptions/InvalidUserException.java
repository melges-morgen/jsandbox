package ru.rusal.exceptions;

/**
 * Exception to indicate that user login and password is incorrect
 * for process a action.
 *
 */
public class InvalidUserException extends Exception {
    private String login;

    private String password;

    /**
     * Default exception constructor.
     */
    public InvalidUserException() {
        super();
    }

    /**
     * Construct exception which save invalid auth information.
     * @param login specified login.
     * @param password specified password in plain text.
     */
    public InvalidUserException(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid credentials: %s/%s", login, password);
    }
}
