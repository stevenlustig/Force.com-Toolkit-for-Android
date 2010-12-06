package com.sforce.android.soap.partner;

import java.util.ArrayList;

import com.sforce.android.soap.partner.sobject.SObject;


public class QueryResult {
	private boolean done;
    private java.lang.String queryLocator;
    private ArrayList<SObject> records=new ArrayList<SObject>();
    private int size;

    public QueryResult() {
    }

    public QueryResult(
           boolean done,
           java.lang.String queryLocator,
           ArrayList<SObject> records,
           int size,
           QueryResult queryResult) {
           this.done = done;
           this.queryLocator = queryLocator;
           this.records = records;
           this.size = size;
    }

    public boolean isDone() {
        return done;
    }
    
    public void setDone(boolean done) {
        this.done = done;
    }
    
    public java.lang.String getQueryLocator() {
        return queryLocator;
    }
    
    public void setQueryLocator(java.lang.String queryLocator) {
        this.queryLocator = queryLocator;
    }
    
    public ArrayList<SObject> getRecords() {
        return records;
    }
    public void setRecords(ArrayList<SObject> records) {
        this.records = records;
    }

    public SObject getRecords(int i) {
        return this.records.get(i);
    }

    public void setRecords(int i, SObject sobject) {
        (this.records).add(i, sobject);
    
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
}
