package com.hiberus.hiring.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Class that represents a sigle structure for all the Back end responses
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class RestApiResponseDTO<T> {

    public static final String SUCCESS = "success";
    
    private String code;
    private ErrorsDTO error;
    private T response;
}
