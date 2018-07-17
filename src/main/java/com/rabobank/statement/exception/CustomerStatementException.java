package com.rabobank.statement.exception;

/**
 * Wrapper exception class for customer statement application
 */
public class CustomerStatementException extends RuntimeException{

    private final String errorMessage = "";
    private final Exception originalException = null;

    public CustomerStatementException(String errorMessage, Exception originalException) {
        super(errorMessage,originalException);
    }
}
