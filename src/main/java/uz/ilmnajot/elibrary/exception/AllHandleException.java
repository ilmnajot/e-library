package uz.ilmnajot.elibrary.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AllHandleException {

    @ExceptionHandler(BaseException.class)
    public HttpEntity<?> exceptionHandler(BaseException baseException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(), baseException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
