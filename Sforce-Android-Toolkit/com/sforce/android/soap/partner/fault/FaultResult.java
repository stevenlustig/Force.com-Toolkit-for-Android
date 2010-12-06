package com.sforce.android.soap.partner.fault;

public class FaultResult {
	private FaultDetail faultDetail;
	private String faultCode;
	private String faultString;
	
	public FaultResult(){
		super();
	}
	public FaultResult(FaultDetail faultDetail, String faultCode, String faultString){
		this.faultDetail=faultDetail;
		this.faultCode=faultCode;
		this.faultString=faultString;
	}
	public FaultDetail getFaultDetail(){
		return this.faultDetail;
	}
	public String getFaultCode(){
		return this.faultCode;
	}
	public String getFaultString(){
		return this.faultString;
	}
	public void setFaultDetail(FaultDetail faultDetail){
		this.faultDetail=faultDetail;
	}
	public void setFaultCode(String faultCode){
		this.faultCode=faultCode;
	}
	public void setFaultString(String faultString){
		this.faultString=faultString;
	}
}
