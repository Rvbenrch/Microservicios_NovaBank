package com.novabank.operacion.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OperacionException.class)
    public ProblemDetail handleOperacionException(OperacionException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problemDetail.setTitle("Operación no válida");
        return problemDetail;
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ProblemDetail handleFeignNotFound(FeignException.NotFound ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                "No se ha encontrado la cuenta indicada"
        );
        problemDetail.setTitle("Cuenta no encontrada");
        return problemDetail;
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    public ProblemDetail handleFeignBadRequest(FeignException.BadRequest ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "La operación no se ha podido completar"
        );
        problemDetail.setTitle("Error en cuenta-service");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Petición no válida");

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                mensaje
        );
        problemDetail.setTitle("Datos inválidos");
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneral(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Se ha producido un error interno en operacion-service"
        );
        problemDetail.setTitle("Error interno");
        return problemDetail;
    }
}