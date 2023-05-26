package za.co.absa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import za.co.absa.exception.*;

@ControllerAdvice
public class GenericAdvice {

    @ExceptionHandler(value = {ProductNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String productNotFoundHandler(ProductNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String userNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {ProductAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String productAlreadyExistHandler(ProductAlreadyExistException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String userAlreadyExistHandler(UserAlreadyExistException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {NotEnoughProductQuantityException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String notEnoughProductQuantityExceptionHandler(NotEnoughProductQuantityException ex) {
        return ex.getMessage();
    }
}
