package za.co.absa.exception;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String name) {
        super("User " + name + " already exist.");
    }
}
