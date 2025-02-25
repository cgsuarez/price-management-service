package com.hiberus.hiring.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hiberus.hiring.controller.dto.ErrorsDTO;
import com.hiberus.hiring.controller.dto.RestApiResponseDTO;
import com.hiberus.hiring.exceptions.OfferNotFoundException;

/**
 * Global Exception handler for the API
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
@RestControllerAdvice
public class OfferServiceExceptionHandler {
    
    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)  
    public ResponseEntity<RestApiResponseDTO<?>> handlOfferNotFoundException(OfferNotFoundException ex) {
        RestApiResponseDTO<?> restServiceResponse = new RestApiResponseDTO<>();
        ErrorsDTO errorsDTO = new ErrorsDTO(ex.getMessage());
        restServiceResponse.setCode("FAILURE");
        restServiceResponse.setError(errorsDTO);
        return new ResponseEntity<>(restServiceResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)    
    public ResponseEntity<Object> handleInternalServerError(Exception ex) { 
        RestApiResponseDTO<?> restServiceResponse = new RestApiResponseDTO<>();
        ErrorsDTO errorsDTO = new ErrorsDTO(ex.getMessage());
        restServiceResponse.setCode("FAILURE");
        restServiceResponse.setError(errorsDTO);
        return new ResponseEntity<>(restServiceResponse, HttpStatus.NOT_FOUND);
    }
}
