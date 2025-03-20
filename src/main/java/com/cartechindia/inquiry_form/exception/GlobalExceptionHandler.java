package com.cartechindia.inquiry_form.exception;

import com.cartechindia.inquiry_form.DTO.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<MessageDTO> exceptionHandler(CustomException ex) {
        MessageDTO message = MessageDTO.builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
