package org.demointernetshop47fs.controller;

import jakarta.validation.ConstraintViolationException;
import org.demointernetshop47fs.service.exception.AlreadyExistException;
import org.demointernetshop47fs.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handlerNullPointerException(NullPointerException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handlerNotFoundException(NotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handlerAlreadyExistException(AlreadyExistException e){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error",e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handlerConstraintViolationException(ConstraintViolationException e) {

        StringBuilder responseMessage = new StringBuilder();

        e.getConstraintViolations().forEach(constraintViolation -> {
            String message = constraintViolation.getMessage();
            responseMessage.append(message).append("\n");
        });

        return new ResponseEntity<>(responseMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handlerSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }




}
