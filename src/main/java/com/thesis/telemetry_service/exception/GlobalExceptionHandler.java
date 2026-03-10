package com.thesis.telemetry_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Setter
@Getter
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler{
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerException(EntityNotFoundException e){
        ErrorResponse err = new ErrorResponse("that vessel not found", System.currentTimeMillis());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
//    @ExceptionHandler(EntityAlreadyExistException.class)
//    public ResponseEntity<ErrorResponse> handlerException(EntityAlreadyExistException e){
//        ErrorResponse err = new ErrorResponse("that vessel already exists", System.currentTimeMillis());
//        return new ResponseEntity<>(err, HttpStatus.NOT_ACCEPTABLE);
//    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerException(MethodArgumentNotValidException e){
        StringBuilder errorMessage = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors){
            errorMessage.append(error.getDefaultMessage()).append("; ");
        }
        ErrorResponse err = new ErrorResponse(errorMessage.toString(), System.currentTimeMillis());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
