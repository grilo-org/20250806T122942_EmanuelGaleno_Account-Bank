package bank.consult.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Tratamento de exceção para erros de argumento inválido
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleInvalidArguments(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // Caso uma exceção inesperada aconteça
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("Deu ruim " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Classe para representar a estrutura do erro retornado
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
