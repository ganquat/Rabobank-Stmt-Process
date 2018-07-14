package com.rabobank.statement.exception;

/**
 * Wrapper exception class for customer statement application
 */
public class CustStmtException extends RuntimeException{

    private String errorMessage = "";
    private Exception originalException;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Exception getOriginalException() {
        return originalException;
    }

    public void setOriginalException(Exception originalException) {
        this.originalException = originalException;
    }

    public CustStmtException(String errorMessage, Exception originalException) {
        this.errorMessage = errorMessage;
        this.originalException = originalException;
    }
}
