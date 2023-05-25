package za.co.absa.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String name) {
        super("Could not find user " + name);
    }

    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}
