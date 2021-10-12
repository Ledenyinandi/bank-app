package nandor.ledenyi.bankapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalAccountTypeException.class})
    public ResponseEntity<String> handleIllegalAccountType() {
        return ResponseEntity.badRequest().body("There is no such account type!");
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFound() {
        return ResponseEntity.badRequest().body("There is no such entity!");
    }
}
