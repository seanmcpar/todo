package com.sean.todo.controller;

import com.sean.todo.dto.ErrorResponseDTO;
import com.sean.todo.exception.NotFoundException;
import com.sean.todo.exception.UpdateInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(final NotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(notFoundException.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(UpdateInvalidException.class)
    public ResponseEntity<ErrorResponseDTO> handleUpdateInvalidException(final UpdateInvalidException updateInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(updateInvalidException.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
