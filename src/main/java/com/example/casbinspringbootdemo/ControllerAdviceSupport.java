package com.example.casbinspringbootdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.NoSuchElementException;

public class ControllerAdviceSupport extends ResponseEntityExceptionHandler {


    Logger LOGGER = LoggerFactory.getLogger(ControllerAdviceSupport.class);

    /**
     * All other handlers should delegate to this method to build and return the {@link ResponseEntity}. This method
     * will add the given error message body, headers and status to the generated response entity.
     * <p>
     *
     * @param headers   the headers for the response
     * @param status    the response status
     * @param errorBody the body for the response
     */
    protected ResponseEntity handleExceptionInternal(HttpHeaders headers, HttpStatus status, Object errorBody
    ) {
        return new ResponseEntity(errorBody, headers, status);
    }

    @Override
    protected ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(headers, status, Map.of("errorMessage", "input not readable"));
    }

    @ExceptionHandler(value = {IllegalStateException.class, IllegalArgumentException.class})
    protected ResponseEntity handle400(Exception ex, WebRequest request) {
        // provide some debuggability for these unhandled exceptions
        LOGGER.debug("unhandled", ex);
        return handleExceptionInternal(new HttpHeaders(), HttpStatus.BAD_REQUEST, Map.of("errorMessage", ex.getMessage()));
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity handle404(Exception ex, WebRequest request) {
        // provide some debuggability for these unhandled exceptions
        LOGGER.debug("unhandled", ex);
        return handleExceptionInternal(new HttpHeaders(), HttpStatus.NOT_FOUND, null);
    }
}
