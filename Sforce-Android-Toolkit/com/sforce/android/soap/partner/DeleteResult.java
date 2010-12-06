package com.sforce.android.soap.partner;

import java.util.ArrayList;

public class DeleteResult {
    private ArrayList<Error> errors=new ArrayList<Error>();

    private String id;

    private boolean success;

    public DeleteResult() {
    }

    public DeleteResult(
           ArrayList<Error> errors,
           String id,
           boolean success) {
           this.errors = errors;
           this.id = id;
           this.success = success;
    }


    /**
     * Gets the errors value for this DeleteResult.
     * 
     * @return errors
     */
    public ArrayList<Error> getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this DeleteResult.
     * 
     * @param errors
     */
    public void setErrors(ArrayList<Error> errors) {
        this.errors = errors;
    }

    public Error getErrors(int i) {
        return this.errors.get(i);
    }

    public void setErrors(int i, Error value) {
        this.errors.add(i, value);
    }


    /**
     * Gets the id value for this DeleteResult.
     * 
     * @return id
     */
    public String getId() {
        return id;
    }


    /**
     * Sets the id value for this DeleteResult.
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Gets the success value for this DeleteResult.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this DeleteResult.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
