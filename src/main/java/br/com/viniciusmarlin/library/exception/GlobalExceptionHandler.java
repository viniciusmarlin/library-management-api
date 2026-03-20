package br.com.viniciusmarlin.library.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* =========================
       ERROS DE REGRA DE NEGÓCIO
       ========================= */

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> handleNotFoundException(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return buildError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    /* =========================
       ERROS DE VALIDAÇÃO (@Valid)
       ========================= */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildError(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI()
        );
    }

    /* =========================
       ERROS DE BANCO
       ========================= */

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        return buildError(
                HttpStatus.CONFLICT,
                "Violação de integridade de dados.",
                request.getRequestURI()
        );
    }

    /* =========================
       ERRO GENÉRICO (fallback)
       ========================= */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno inesperado.",
                request.getRequestURI()
        );
    }

    /* =========================
       metodo auxiliar
       ========================= */

    private ResponseEntity<StandardError> buildError(
            HttpStatus status,
            String message,
            String path
    ) {
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();

        return ResponseEntity.status(status).body(error);
    }
}