package com.rabobank.statement.exception;

/**
 * Created by Ganesh_C01 on 7/13/2018.
 */
public class CustStmtException extends RuntimeException{

    private String strErrorMessage = "";
    private Exception originalException;

    public String getStrErrorMessage() {
        return strErrorMessage;
    }

    public void setStrErrorMessage(String strErrorMessage) {
        this.strErrorMessage = strErrorMessage;
    }

    public Exception getOriginalException() {
        return originalException;
    }

    public void setOriginalException(Exception originalException) {
        this.originalException = originalException;
    }

    /**
     * @param strErrorMessage
     * @param originalException
     */
    public CustStmtException(String strErrorMessage, Exception originalException) {
        this.strErrorMessage = strErrorMessage;
        this.originalException = originalException;
    }
}
