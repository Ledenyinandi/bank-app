package nandor.ledenyi.bankapp.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFound() {
        return ResponseEntity.badRequest().body("There is no such entity!");
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<String> handleConstraintViolation() {
        return ResponseEntity.badRequest().body("Entity can not be deleted!");
    }

    @ExceptionHandler(value = InsufficientCreditException.class)
    public ResponseEntity<String> handleInsufficientCreditException() {
        return ResponseEntity.badRequest().body("Insufficient credit!");
    }

    @ExceptionHandler(value = IdenticalIdException.class)
    public ResponseEntity<String> handleIdenticalIdException() {
        return ResponseEntity.badRequest().body("Identical ids!");
    }

    @ExceptionHandler(value = AccountNumberNotFoundException.class)
    public ResponseEntity<String> handleAccountNumberNotFoundException() {
        return ResponseEntity.badRequest().body("Account number not found!");
    }
}
