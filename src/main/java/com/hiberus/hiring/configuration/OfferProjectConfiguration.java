package com.hiberus.hiring.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
@Configuration
public class OfferProjectConfiguration {
    
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }


}
