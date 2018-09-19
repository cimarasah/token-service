package com.cimarasah.token.web.handler;

import com.cimarasah.token.domain.model.TokenNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class RestErrorExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        sendLogs(ex);
        ex.printStackTrace();

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), errors);
        return handleExceptionInternal(ex, errorMessage, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        sendLogs(ex);
        ex.printStackTrace();

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ex.getBindingResult().getGlobalErrors()
                .stream()
                .map(error -> error.getObjectName() + ": " + error.getDefaultMessage())
                .forEachOrdered(errors::add);

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), errors);
        return handleExceptionInternal(ex, errorMessage, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        sendLogs(ex);
        ex.printStackTrace();

        String message = "Não foi possível converter o valor " + ex.getValue();
        if (ex.getMostSpecificCause() != null || ex.getMostSpecificCause().getMessage() != null) {
            message = ex.getMostSpecificCause().getMessage();
        }

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED.value(), message);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        sendLogs(ex);
        ex.printStackTrace();

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        sendLogs(ex);
        ex.printStackTrace();

        String error = "Recurso " + ex.getHttpMethod() + " " + ex.getRequestURL() + " não encontrado";
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), error);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, final WebRequest request) {
        sendLogs(ex);
        ex.printStackTrace();

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), errors);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TokenNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(TokenNotFoundException ex) {
        sendLogs(ex);
        ex.printStackTrace();

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleBadRequestExceptions(RuntimeException ex) {
        sendLogs(ex);
        ex.printStackTrace();

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        sendLogs(ex);
        ex.printStackTrace();

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro ao processar a identificação, tente novamente !");
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        sendLogs(ex);
        ex.printStackTrace();

        Object message = "";
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException exception = (InvalidFormatException) ex.getCause();
            message = exception.getValue();
        }

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                "Verifique o contrato de envio, não foi possível fazer a conversão do valor " + message);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private void sendLogs(Exception ex) {
//        logger.error("{} {}", PREFIX_LOG, ex.getClass().getName());
//        logger.error("{} {}", PREFIX_LOG, ex.getMessage());
//        logger.error("{} {}", PREFIX_LOG, ex.getLocalizedMessage());
    }
}