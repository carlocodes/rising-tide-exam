package com.carlocodes.rising_tide_exam.exceptions;

import com.carlocodes.rising_tide_exam.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto> handleValidationException(ValidationException ve) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setTransactionStatusCode(HttpStatus.BAD_REQUEST.value());
        responseDto.setTransactionStatusDescription(ve.getMessage());
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseDto> handleCustomerNotFoundException(CustomerNotFoundException cnfe) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setTransactionStatusCode(HttpStatus.UNAUTHORIZED.value());
        responseDto.setTransactionStatusDescription(cnfe.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }
}
