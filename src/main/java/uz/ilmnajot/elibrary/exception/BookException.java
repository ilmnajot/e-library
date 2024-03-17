package uz.ilmnajot.elibrary.exception;

import org.springframework.http.HttpStatus;

public class BookException extends BaseException{
    public BookException(String message) {
        super(message);
    }

    public BookException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public BookException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}
