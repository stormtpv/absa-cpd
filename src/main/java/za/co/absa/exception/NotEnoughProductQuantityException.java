package za.co.absa.exception;

public class NotEnoughProductQuantityException extends RuntimeException {

    public NotEnoughProductQuantityException(Long quantity, Long unitsToBuy) {
        super("Not enough product quantity, available " + quantity + " item(s), required " + unitsToBuy);
    }
}
