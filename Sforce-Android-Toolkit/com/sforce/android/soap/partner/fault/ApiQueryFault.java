package com.sforce.android.soap.partner.fault;

public class ApiQueryFault extends ApiFault {
    private int row;
    private int column;

    public ApiQueryFault() {
    }

    public ApiQueryFault(
           ExceptionCode exceptionCode,
           java.lang.String exceptionMessage,
           int row,
           int column) {
        super(exceptionCode, exceptionMessage);
        this.row = row;
        this.column = column;
    }

    /**
     * Gets the row value for this ApiQueryFault.
     * 
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row value for this ApiQueryFault.
     * 
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }


    /**
     * Gets the column value for this ApiQueryFault.
     * 
     * @return column
     */
    public int getColumn() {
        return column;
    }


    /**
     * Sets the column value for this ApiQueryFault.
     * 
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    }
}
