package com.example.user.exception;

import org.aspectj.apache.bcel.ExceptionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value={IdNotFoundException.class})
    public ResponseEntity<ApiResponse> HandleIdNotFoundException(IdNotFoundException ide){

        ApiResponse response= ApiResponse.builder()
                .message(ide.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .data(null)
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value={EmailIdExistsException.class})
    public ResponseEntity<ApiResponse> HandleEmailIdExistsException(EmailIdExistsException ee){

        ApiResponse response= ApiResponse.builder()
                .message(ee.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={NoDataFoundException.class})
    public ResponseEntity<ApiResponse>handleNoDataFoundException(NoDataFoundException ndf){
        ApiResponse response=ApiResponse.builder()
                .message(ndf.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .data(null)
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value={RuntimeException.class})
    public ResponseEntity<ApiResponse> handleGenericException(RuntimeException e){
        ApiResponse response=ApiResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
