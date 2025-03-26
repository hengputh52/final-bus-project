package busbus;

public interface Authenticates {
    void signUp();
    boolean login(String email, String password);
    void logout();
}
