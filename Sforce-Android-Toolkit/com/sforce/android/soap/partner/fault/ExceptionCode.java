/**
 * ExceptionCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.sforce.android.soap.partner.fault;

import java.io.ObjectStreamException;
import java.util.HashMap;

public class ExceptionCode {
    private String value;
    private static HashMap<String, ExceptionCode> table = new java.util.HashMap<String, ExceptionCode>();

    // Constructor
    protected ExceptionCode(String value) {
        this.value = value;
        table.put(value,this);
    }
    
    public String getValue() { 
    	return value;
    }
    
    public static ExceptionCode fromValue(String value) throws IllegalArgumentException {
        ExceptionCode enumeration = (ExceptionCode) table.get(value);
        if (enumeration==null)
        	enumeration = new ExceptionCode(value);
        return enumeration;
    }
    
    public static ExceptionCode fromString(String value) throws IllegalArgumentException {
        return fromValue(value);
    }

    public String toString() { 
    	return value;
    }
    
    public Object readResolve() throws ObjectStreamException { 
    	return fromValue(value);
    }
    
    public static final String _API_CURRENTLY_DISABLED = "API_CURRENTLY_DISABLED";
    public static final String _API_DISABLED_FOR_ORG = "API_DISABLED_FOR_ORG";
    public static final String _CLIENT_NOT_ACCESSIBLE_FOR_USER = "CLIENT_NOT_ACCESSIBLE_FOR_USER";
    public static final String _CLIENT_REQUIRE_UPDATE_FOR_USER = "CLIENT_REQUIRE_UPDATE_FOR_USER";
    public static final String _EMAIL_BATCH_SIZE_LIMIT_EXCEEDED = "EMAIL_BATCH_SIZE_LIMIT_EXCEEDED";
    public static final String _EMAIL_TO_CASE_INVALID_ROUTING = "EMAIL_TO_CASE_INVALID_ROUTING";
    public static final String _EMAIL_TO_CASE_LIMIT_EXCEEDED = "EMAIL_TO_CASE_LIMIT_EXCEEDED";
    public static final String _EMAIL_TO_CASE_NOT_ENABLED = "EMAIL_TO_CASE_NOT_ENABLED";
    public static final String _EXCEEDED_ID_LIMIT = "EXCEEDED_ID_LIMIT";
    public static final String _EXCEEDED_LEAD_CONVERT_LIMIT = "EXCEEDED_LEAD_CONVERT_LIMIT";
    public static final String _EXCEEDED_MAX_SIZE_REQUEST = "EXCEEDED_MAX_SIZE_REQUEST";
    public static final String _EXCEEDED_MAX_TYPES_LIMIT = "EXCEEDED_MAX_TYPES_LIMIT";
    public static final String _EXCEEDED_QUOTA = "EXCEEDED_QUOTA";
    public static final String _FUNCTIONALITY_NOT_ENABLED = "FUNCTIONALITY_NOT_ENABLED";
    public static final String _INACTIVE_OWNER_OR_USER = "INACTIVE_OWNER_OR_USER";
    public static final String _INSUFFICIENT_ACCESS = "INSUFFICIENT_ACCESS";
    public static final String _INVALID_ASSIGNMENT_RULE = "INVALID_ASSIGNMENT_RULE";
    public static final String _INVALID_BATCH_SIZE = "INVALID_BATCH_SIZE";
    public static final String _INVALID_CLIENT = "INVALID_CLIENT";
    public static final String _INVALID_CROSS_REFERENCE_KEY = "INVALID_CROSS_REFERENCE_KEY";
    public static final String _INVALID_FIELD = "INVALID_FIELD";
    public static final String _INVALID_ID_FIELD = "INVALID_ID_FIELD";
    public static final String _INVALID_LOCATOR = "INVALID_LOCATOR";
    public static final String _INVALID_LOGIN = "INVALID_LOGIN";
    public static final String _INVALID_NEW_PASSWORD = "INVALID_NEW_PASSWORD";
    public static final String _INVALID_OPERATION = "INVALID_OPERATION";
    public static final String _INVALID_OPERATION_WITH_EXPIRED_PASSWORD = "INVALID_OPERATION_WITH_EXPIRED_PASSWORD";
    public static final String _INVALID_QUERY_FILTER_OPERATOR = "INVALID_QUERY_FILTER_OPERATOR";
    public static final String _INVALID_QUERY_LOCATOR = "INVALID_QUERY_LOCATOR";
    public static final String _INVALID_QUERY_SCOPE = "INVALID_QUERY_SCOPE";
    public static final String _INVALID_REPLICATION_DATE = "INVALID_REPLICATION_DATE";
    public static final String _INVALID_SEARCH = "INVALID_SEARCH";
    public static final String _INVALID_SEARCH_SCOPE = "INVALID_SEARCH_SCOPE";
    public static final String _INVALID_SESSION_ID = "INVALID_SESSION_ID";
    public static final String _INVALID_SOAP_HEADER = "INVALID_SOAP_HEADER";
    public static final String _INVALID_SSO_GATEWAY_URL = "INVALID_SSO_GATEWAY_URL";
    public static final String _INVALID_TYPE = "INVALID_TYPE";
    public static final String _INVALID_TYPE_FOR_OPERATION = "INVALID_TYPE_FOR_OPERATION";
    public static final String _LOGIN_DURING_RESTRICTED_DOMAIN = "LOGIN_DURING_RESTRICTED_DOMAIN";
    public static final String _LOGIN_DURING_RESTRICTED_TIME = "LOGIN_DURING_RESTRICTED_TIME";
    public static final String _MALFORMED_ID = "MALFORMED_ID";
    public static final String _MALFORMED_QUERY = "MALFORMED_QUERY";
    public static final String _MALFORMED_SEARCH = "MALFORMED_SEARCH";
    public static final String _MISSING_ARGUMENT = "MISSING_ARGUMENT";
    public static final String _NOT_MODIFIED = "NOT_MODIFIED";
    public static final String _NUMBER_OUTSIDE_VALID_RANGE = "NUMBER_OUTSIDE_VALID_RANGE";
    public static final String _OPERATION_TOO_LARGE = "OPERATION_TOO_LARGE";
    public static final String _ORG_LOCKED = "ORG_LOCKED";
    public static final String _PASSWORD_LOCKOUT = "PASSWORD_LOCKOUT";
    public static final String _QUERY_TIMEOUT = "QUERY_TIMEOUT";
    public static final String _QUERY_TOO_COMPLICATED = "QUERY_TOO_COMPLICATED";
    public static final String _REQUEST_LIMIT_EXCEEDED = "REQUEST_LIMIT_EXCEEDED";
    public static final String _REQUEST_RUNNING_TOO_LONG = "REQUEST_RUNNING_TOO_LONG";
    public static final String _SERVER_UNAVAILABLE = "SERVER_UNAVAILABLE";
    public static final String _SSO_SERVICE_DOWN = "SSO_SERVICE_DOWN";
    public static final String _TRIAL_EXPIRED = "TRIAL_EXPIRED";
    public static final String _UNKNOWN_EXCEPTION = "UNKNOWN_EXCEPTION";
    public static final String _UNSUPPORTED_API_VERSION = "UNSUPPORTED_API_VERSION";
    public static final String _UNSUPPORTED_CLIENT = "UNSUPPORTED_CLIENT";
    
    /*OAuth error codes*/
    public static final String _UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
    public static final String _INVALID_CLIENT_ID = "invalid_client_id";
    public static final String _INVALID_REQUEST = "invalid_request";
    public static final String _ACCESS_DENIED = "access_denied";
    public static final String _REDIRECT_URI_MISSING = "redirect_uri_missing";
    public static final String _REDIRECT_URI_MISMATCH = "redirect_uri_mismatch";
    public static final String _INVALID_GRANT = "invalid_grant";
    
    public static final ExceptionCode API_CURRENTLY_DISABLED = new ExceptionCode(_API_CURRENTLY_DISABLED);
    public static final ExceptionCode API_DISABLED_FOR_ORG = new ExceptionCode(_API_DISABLED_FOR_ORG);
    public static final ExceptionCode CLIENT_NOT_ACCESSIBLE_FOR_USER = new ExceptionCode(_CLIENT_NOT_ACCESSIBLE_FOR_USER);
    public static final ExceptionCode CLIENT_REQUIRE_UPDATE_FOR_USER = new ExceptionCode(_CLIENT_REQUIRE_UPDATE_FOR_USER);
    public static final ExceptionCode EMAIL_BATCH_SIZE_LIMIT_EXCEEDED = new ExceptionCode(_EMAIL_BATCH_SIZE_LIMIT_EXCEEDED);
    public static final ExceptionCode EMAIL_TO_CASE_INVALID_ROUTING = new ExceptionCode(_EMAIL_TO_CASE_INVALID_ROUTING);
    public static final ExceptionCode EMAIL_TO_CASE_LIMIT_EXCEEDED = new ExceptionCode(_EMAIL_TO_CASE_LIMIT_EXCEEDED);
    public static final ExceptionCode EMAIL_TO_CASE_NOT_ENABLED = new ExceptionCode(_EMAIL_TO_CASE_NOT_ENABLED);
    public static final ExceptionCode EXCEEDED_ID_LIMIT = new ExceptionCode(_EXCEEDED_ID_LIMIT);
    public static final ExceptionCode EXCEEDED_LEAD_CONVERT_LIMIT = new ExceptionCode(_EXCEEDED_LEAD_CONVERT_LIMIT);
    public static final ExceptionCode EXCEEDED_MAX_SIZE_REQUEST = new ExceptionCode(_EXCEEDED_MAX_SIZE_REQUEST);
    public static final ExceptionCode EXCEEDED_MAX_TYPES_LIMIT = new ExceptionCode(_EXCEEDED_MAX_TYPES_LIMIT);
    public static final ExceptionCode EXCEEDED_QUOTA = new ExceptionCode(_EXCEEDED_QUOTA);
    public static final ExceptionCode FUNCTIONALITY_NOT_ENABLED = new ExceptionCode(_FUNCTIONALITY_NOT_ENABLED);
    public static final ExceptionCode INACTIVE_OWNER_OR_USER = new ExceptionCode(_INACTIVE_OWNER_OR_USER);
    public static final ExceptionCode INSUFFICIENT_ACCESS = new ExceptionCode(_INSUFFICIENT_ACCESS);
    public static final ExceptionCode INVALID_ASSIGNMENT_RULE = new ExceptionCode(_INVALID_ASSIGNMENT_RULE);
    public static final ExceptionCode INVALID_BATCH_SIZE = new ExceptionCode(_INVALID_BATCH_SIZE);
    public static final ExceptionCode INVALID_CLIENT = new ExceptionCode(_INVALID_CLIENT);
    public static final ExceptionCode INVALID_CROSS_REFERENCE_KEY = new ExceptionCode(_INVALID_CROSS_REFERENCE_KEY);
    public static final ExceptionCode INVALID_FIELD = new ExceptionCode(_INVALID_FIELD);
    public static final ExceptionCode INVALID_ID_FIELD = new ExceptionCode(_INVALID_ID_FIELD);
    public static final ExceptionCode INVALID_LOCATOR = new ExceptionCode(_INVALID_LOCATOR);
    public static final ExceptionCode INVALID_LOGIN = new ExceptionCode(_INVALID_LOGIN);
    public static final ExceptionCode INVALID_NEW_PASSWORD = new ExceptionCode(_INVALID_NEW_PASSWORD);
    public static final ExceptionCode INVALID_OPERATION = new ExceptionCode(_INVALID_OPERATION);
    public static final ExceptionCode INVALID_OPERATION_WITH_EXPIRED_PASSWORD = new ExceptionCode(_INVALID_OPERATION_WITH_EXPIRED_PASSWORD);
    public static final ExceptionCode INVALID_QUERY_FILTER_OPERATOR = new ExceptionCode(_INVALID_QUERY_FILTER_OPERATOR);
    public static final ExceptionCode INVALID_QUERY_LOCATOR = new ExceptionCode(_INVALID_QUERY_LOCATOR);
    public static final ExceptionCode INVALID_QUERY_SCOPE = new ExceptionCode(_INVALID_QUERY_SCOPE);
    public static final ExceptionCode INVALID_REPLICATION_DATE = new ExceptionCode(_INVALID_REPLICATION_DATE);
    public static final ExceptionCode INVALID_SEARCH = new ExceptionCode(_INVALID_SEARCH);
    public static final ExceptionCode INVALID_SEARCH_SCOPE = new ExceptionCode(_INVALID_SEARCH_SCOPE);
    public static final ExceptionCode INVALID_SESSION_ID = new ExceptionCode(_INVALID_SESSION_ID);
    public static final ExceptionCode INVALID_SOAP_HEADER = new ExceptionCode(_INVALID_SOAP_HEADER);
    public static final ExceptionCode INVALID_SSO_GATEWAY_URL = new ExceptionCode(_INVALID_SSO_GATEWAY_URL);
    public static final ExceptionCode INVALID_TYPE = new ExceptionCode(_INVALID_TYPE);
    public static final ExceptionCode INVALID_TYPE_FOR_OPERATION = new ExceptionCode(_INVALID_TYPE_FOR_OPERATION);
    public static final ExceptionCode LOGIN_DURING_RESTRICTED_DOMAIN = new ExceptionCode(_LOGIN_DURING_RESTRICTED_DOMAIN);
    public static final ExceptionCode LOGIN_DURING_RESTRICTED_TIME = new ExceptionCode(_LOGIN_DURING_RESTRICTED_TIME);
    public static final ExceptionCode MALFORMED_ID = new ExceptionCode(_MALFORMED_ID);
    public static final ExceptionCode MALFORMED_QUERY = new ExceptionCode(_MALFORMED_QUERY);
    public static final ExceptionCode MALFORMED_SEARCH = new ExceptionCode(_MALFORMED_SEARCH);
    public static final ExceptionCode MISSING_ARGUMENT = new ExceptionCode(_MISSING_ARGUMENT);
    public static final ExceptionCode NOT_MODIFIED = new ExceptionCode(_NOT_MODIFIED);
    public static final ExceptionCode NUMBER_OUTSIDE_VALID_RANGE = new ExceptionCode(_NUMBER_OUTSIDE_VALID_RANGE);
    public static final ExceptionCode OPERATION_TOO_LARGE = new ExceptionCode(_OPERATION_TOO_LARGE);
    public static final ExceptionCode ORG_LOCKED = new ExceptionCode(_ORG_LOCKED);
    public static final ExceptionCode PASSWORD_LOCKOUT = new ExceptionCode(_PASSWORD_LOCKOUT);
    public static final ExceptionCode QUERY_TIMEOUT = new ExceptionCode(_QUERY_TIMEOUT);
    public static final ExceptionCode QUERY_TOO_COMPLICATED = new ExceptionCode(_QUERY_TOO_COMPLICATED);
    public static final ExceptionCode REQUEST_LIMIT_EXCEEDED = new ExceptionCode(_REQUEST_LIMIT_EXCEEDED);
    public static final ExceptionCode REQUEST_RUNNING_TOO_LONG = new ExceptionCode(_REQUEST_RUNNING_TOO_LONG);
    public static final ExceptionCode SERVER_UNAVAILABLE = new ExceptionCode(_SERVER_UNAVAILABLE);
    public static final ExceptionCode SSO_SERVICE_DOWN = new ExceptionCode(_SSO_SERVICE_DOWN);
    public static final ExceptionCode TRIAL_EXPIRED = new ExceptionCode(_TRIAL_EXPIRED);
    public static final ExceptionCode UNKNOWN_EXCEPTION = new ExceptionCode(_UNKNOWN_EXCEPTION);
    public static final ExceptionCode UNSUPPORTED_API_VERSION = new ExceptionCode(_UNSUPPORTED_API_VERSION);
    public static final ExceptionCode UNSUPPORTED_CLIENT = new ExceptionCode(_UNSUPPORTED_CLIENT);

    public static final ExceptionCode UNSUPPORTED_RESPONSE_TYPE = new ExceptionCode(_UNSUPPORTED_RESPONSE_TYPE);
    public static final ExceptionCode INVALID_CLIENT_ID = new ExceptionCode(_INVALID_CLIENT_ID);
    public static final ExceptionCode INVALID_REQUEST = new ExceptionCode(_INVALID_REQUEST);
    public static final ExceptionCode ACCESS_DENIED = new ExceptionCode(_ACCESS_DENIED);
    public static final ExceptionCode REDIRECT_URI_MISSING = new ExceptionCode(_REDIRECT_URI_MISSING);
    public static final ExceptionCode REDIRECT_URI_MISMATCH = new ExceptionCode(_REDIRECT_URI_MISMATCH);
    public static final ExceptionCode INVALID_GRANT = new ExceptionCode(_INVALID_GRANT);
}
