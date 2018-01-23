package agileproject;

import org.axonframework.messaging.interceptors.JSR303ViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JSR303ViolationException.class)
    public ResponseEntity<Void> handleException() {

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
