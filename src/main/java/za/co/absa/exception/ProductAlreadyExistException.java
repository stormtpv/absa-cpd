package za.co.absa.exception;

public class ProductAlreadyExistException extends RuntimeException {

    public ProductAlreadyExistException(String name) {
        super("Product " + name + " already exist");
    }

}
