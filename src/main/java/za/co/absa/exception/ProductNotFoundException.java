package za.co.absa.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String name) {
        super("Could not find product " + name);
    }

    public ProductNotFoundException(Long id) {
        super("Could not find product " + id);
    }
}
