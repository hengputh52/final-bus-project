package busbus;

public class InvalidLoginException extends Exception {
    public InvalidLoginException() {
        super("Invalid username or password.");
    }
}