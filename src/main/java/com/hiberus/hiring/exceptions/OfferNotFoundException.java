package com.hiberus.hiring.exceptions;

/**
 * Class that represents the exception when an offer is not found on the
 * persistence layer
 * @author Christian Suarez
 * @version 1.0.0
 * @since 24-Feb-2025
 */
public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException(String message) {
        super(message);
    }

}
