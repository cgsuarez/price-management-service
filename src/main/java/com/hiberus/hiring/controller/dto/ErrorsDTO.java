package com.hiberus.hiring.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class which represent an error message
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDTO {
    
    private String errorMessage;
}
