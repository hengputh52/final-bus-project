package busbus;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String message) {
        super("Customer with email " + message + " already exists.");
    }
}