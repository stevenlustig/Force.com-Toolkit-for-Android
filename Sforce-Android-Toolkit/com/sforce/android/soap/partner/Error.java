package com.sforce.android.soap.partner;

import java.util.ArrayList;

public class Error {
    private ArrayList<String> fields;

    private String message;

    private StatusCode statusCode;

    public Error() {
    }

    public Error(
           ArrayList<String> fields,
           String message,
           StatusCode statusCode) {
           this.fields = fields;
           this.message = message;
           this.statusCode = statusCode;
    }


    /**
     * Gets the fields value for this Error.
     * 
     * @return fields
     */
    public ArrayList<String> getFields() {
        return fields;
    }


    /**
     * Sets the fields value for this Error.
     * 
     * @param fields
     */
    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    public java.lang.String getFields(int i) {
        return this.fields.get(i);
    }

    public void setFields(int i, String value) {
        this.fields.set(i,value);
    }


    /**
     * Gets the message value for this Error.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Error.
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Gets the statusCode value for this Error.
     * 
     * @return statusCode
     */
    public StatusCode getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this Error.
     * 
     * @param statusCode
     */
    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

}
