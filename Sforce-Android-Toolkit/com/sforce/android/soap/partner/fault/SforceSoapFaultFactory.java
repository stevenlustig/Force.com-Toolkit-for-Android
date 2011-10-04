package com.sforce.android.soap.partner.fault;

import com.sforce.android.soap.partner.Response;

public final class SforceSoapFaultFactory extends FaultFactory{
	public static ApiFault getSoapFault(String faultType, Response response){
		final FaultSoapResponse fsr=(FaultSoapResponse) response;
		if (faultType.equals("query")){
	    	FaultResult fr=fsr.getResult();
	    	FaultDetail fd=fr.getFaultDetail();
			String excpCode = (fd != null)?fd.getExceptionCode():fr.getFaultCode();
			String excpMsg = (fd != null)?fd.getExceptionMessage():fr.getFaultString();
	    	ExceptionCode exceptionCode=new ExceptionCode(excpCode);
	    	int row = -1;
	    	int column = -1;
	    	if (fd.getRow() != null) {
	    		row = Integer.parseInt(fd.getRow());
	    	}
	    	if (fd.getColumn() != null) {
	    		column=Integer.parseInt(fd.getColumn());
	    	}
	    	return new ApiQueryFault(exceptionCode, excpMsg, row, column);
		} else {
			FaultResult fr=fsr.getResult();
			FaultDetail fd=fr.getFaultDetail();
			String excpCode = (fd != null)?fd.getExceptionCode():fr.getFaultCode();
			String excpMsg = (fd != null)?fd.getExceptionMessage():fr.getFaultString();
	    	ExceptionCode exceptionCode=new ExceptionCode(excpCode);
	    	return new ApiFault(exceptionCode, excpMsg);
		}
	}
}
