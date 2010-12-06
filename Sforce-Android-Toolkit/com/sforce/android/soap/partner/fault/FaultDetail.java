package com.sforce.android.soap.partner.fault;

public class FaultDetail {
	private String exceptionCode;
	private String exceptionMessage;
	private String row;
	private String column;
	
	public FaultDetail(){
		super();
	}
	
	public FaultDetail(String exceptionCode, String exceptionMessage, String row, String column){
		this.exceptionCode=exceptionCode;
		this.exceptionMessage=exceptionMessage;
		this.row=row;
		this.column=column;
	}
	
	public String getExceptionCode(){
		return this.exceptionCode;
	}
	
	public String getExceptionMessage(){
		return this.exceptionMessage;
	}
	
	public String getRow(){
		return this.row;
	}
	
	public String getColumn(){
		return this.column;
	}
	
	public void setExceptionCode(String exceptionCode){
		this.exceptionCode=exceptionCode;
	}
	
	public void setExceptionMessage(String exceptionMessage){
		this.exceptionMessage=exceptionMessage;
	}
	
	public void setRow(String row){
		this.row=row;
	}
	
	public void setColumn(String column){
		this.column=column;
	}
}
