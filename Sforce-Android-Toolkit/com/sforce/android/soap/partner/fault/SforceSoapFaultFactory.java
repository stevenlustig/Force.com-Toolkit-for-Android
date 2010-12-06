package com.sforce.android.soap.partner.fault;

import com.sforce.android.soap.partner.Response;

public final class SforceSoapFaultFactory extends FaultFactory{
	public static ApiFault getSoapFault(String faultType, Response response){
		final FaultSoapResponse fsr=(FaultSoapResponse) response;
		if (faultType.equals("query")){
	    	FaultResult fr=fsr.getResult();
	    	FaultDetail fd=fr.getFaultDetail();
	    	ExceptionCode exceptionCode=new ExceptionCode(fd.getExceptionCode());
	    	String exceptionMessage=fd.getExceptionMessage();
	    	int row=Integer.parseInt(fd.getRow());
	    	int column=Integer.parseInt(fd.getColumn());
	    	return new ApiQueryFault(exceptionCode, exceptionMessage, row, column);
		} else {
			FaultResult fr=fsr.getResult();
			FaultDetail fd=fr.getFaultDetail();
	    	ExceptionCode exceptionCode=new ExceptionCode(fd.getExceptionCode());
	    	String exceptionMessage=fd.getExceptionMessage();
	    	return new ApiFault(exceptionCode, exceptionMessage);
		}
	}
}
