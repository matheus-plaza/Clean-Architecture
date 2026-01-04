package io.github.matheusplaza.clean_architecture.infra.config.error;

import io.github.matheusplaza.clean_architecture.core.exceptions.EventAlreadyExistsException;
import io.github.matheusplaza.clean_architecture.core.exceptions.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EventNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(EventAlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExists(EventAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleBadRequest(HttpMessageNotReadableException e){

        if (e.getCause() instanceof InvalidFormatException invalidFormat) {

            if (invalidFormat.getTargetType().isEnum()) {
                String invalidValue = invalidFormat.getValue().toString();
                String validValues = Arrays.toString(invalidFormat.getTargetType().getEnumConstants());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format("Invalid value '%s'. The accepted values are: %s",
                                invalidValue, validValues));
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Malformed request body");
    }

}
