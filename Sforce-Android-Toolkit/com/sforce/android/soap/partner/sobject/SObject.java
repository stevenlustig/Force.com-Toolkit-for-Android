package com.sforce.android.soap.partner.sobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import java.io.Serializable;

public class SObject implements Serializable {
		private String type;
	    private ArrayList<String> fieldsToNull;
	    private String id;
	    private HashMap<String, String> fields=new HashMap<String, String>();
	    public SObject() {
	    	super();
	    }

	    public SObject(String type, ArrayList<String> fieldsToNull, String id, HashMap<String, String> fields) {
	           this.type = type;
	           this.fieldsToNull = fieldsToNull;
	           this.id = id;
	           this.fields=fields;
	    }
	    public java.lang.String getType() {
	        return type;
	    }
	    public void setType(java.lang.String type) {
	        this.type = type;
	    }
	    public ArrayList<String> getFieldsToNull() {
	        return fieldsToNull;
	    }
	    public void setFieldsToNull(ArrayList<String> fieldsToNull) {
	        this.fieldsToNull = fieldsToNull;
	    }

	    public java.lang.String getFieldsToNull(int i) {
	        return this.fieldsToNull.get(i);
	    }

	    public void setFieldsToNull(int i, java.lang.String _value) {
	        this.fieldsToNull.add(i, _value);
	    }

	    public java.lang.String getId() {
	        return id;
	    }

	    public void setId(java.lang.String id) {
	        this.id = id;
	    }
	    
	    public HashMap<String, String> getFields(){
	    	return this.fields;
	    }
	    
	    public void setFields(HashMap<String, String> fields){
	    	this.fields=fields;
	    }

	    public String getField(String fieldName){
	    	return this.fields.get(fieldName.toLowerCase());
	    }
	    
	    public void setField(String fieldName, String fieldValue){
	    	this.fields.put(fieldName.toLowerCase(), fieldValue);
	    }
	    StringBuffer sb=new StringBuffer();
	    public String toString(){
	    	sb.append("****************************\n");
	    	sb.append("Type:").append(type).append("\n").append("Id:").append(id).append("\n");

	    	Set<String> fieldSet=fields.keySet();
			Iterator<String> itrFields=fieldSet.iterator();

			while (itrFields.hasNext()){
				String fieldName=itrFields.next();
				sb.append(fieldName).append(": ").append(fields.get(fieldName)).append("\n");
	    	}
			return sb.toString();
	    }
}
