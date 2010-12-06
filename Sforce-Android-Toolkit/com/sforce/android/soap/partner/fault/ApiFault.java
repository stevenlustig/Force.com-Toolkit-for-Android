package com.sforce.android.soap.partner.fault;

public class ApiFault extends Exception {
    private ExceptionCode exceptionCode;
    private String exceptionMessage;

    public ApiFault() {
    }

    public ApiFault(ExceptionCode exceptionCode, String exceptionMessage) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }


    /**
     * Gets the exceptionCode value for this ApiFault.
     * 
     * @return exceptionCode
     */
    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }


    /**
     * Sets the exceptionCode value for this ApiFault.
     * 
     * @param exceptionCode
     */
    public void setExceptionCode(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }


    /**
     * Gets the exceptionMessage value for this ApiFault.
     * 
     * @return exceptionMessage
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }


    /**
     * Sets the exceptionMessage value for this ApiFault.
     * 
     * @param exceptionMessage
     */
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
