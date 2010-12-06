package com.sforce.android.soap.partner;

import java.util.ArrayList;

public class UpsertResult {
    private boolean created;

    private ArrayList<Error> errors=new ArrayList<Error>();

    private java.lang.String id;

    private boolean success;

    public UpsertResult() {
    }

    public UpsertResult(
           boolean created,
           ArrayList<Error> errors,
           java.lang.String id,
           boolean success) {
           this.created = created;
           this.errors = errors;
           this.id = id;
           this.success = success;
    }


    /**
     * Gets the created value for this UpsertResult.
     * 
     * @return created
     */
    public boolean isCreated() {
        return created;
    }


    /**
     * Sets the created value for this UpsertResult.
     * 
     * @param created
     */
    public void setCreated(boolean created) {
        this.created = created;
    }


    /**
     * Gets the errors value for this SaveResult.
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
     * Gets the id value for this UpsertResult.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this UpsertResult.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the success value for this UpsertResult.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this UpsertResult.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
