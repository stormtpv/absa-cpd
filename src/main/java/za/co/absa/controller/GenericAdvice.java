package za.co.absa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import za.co.absa.exception.ProductNotFoundException;
import za.co.absa.exception.UserNotFoundException;

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
}
