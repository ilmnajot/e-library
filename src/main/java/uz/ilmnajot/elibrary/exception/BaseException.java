package uz.ilmnajot.elibrary.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    private HttpStatus httpStatus;

    public BaseException(String message){
        super(message);
    }
    public BaseException(String message, HttpStatus httpStatus){
    super(message);
    this.httpStatus = httpStatus;
    }
    public BaseException(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }
}
