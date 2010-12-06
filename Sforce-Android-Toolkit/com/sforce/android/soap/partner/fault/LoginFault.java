/**
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.sforce.android.soap.partner.fault;

public class LoginFault  extends ApiFault {
	static final String EXCEPTION_CODE="exceptionCode";
	static final String EXCEPTION_MESSAGE="exceptionMessage";
	static final String FAULT="Fault";

	public LoginFault(
           ExceptionCode exceptionCode,
           String exceptionMessage) {
        super(
            exceptionCode,
            exceptionMessage);
    }    
}
