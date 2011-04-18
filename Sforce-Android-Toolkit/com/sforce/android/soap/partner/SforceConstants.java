package com.sforce.android.soap.partner;

/**
 * Salesforce Constants used in AsyncSforce and a few other classes.
 * @author siddhu.warrier
 *
 */
public class SforceConstants {
	public static final String SOAP_ACTION = "soapaction";
	public static final String SOAP_ACTION_VALUE = "\"\"";
	public static final String SOAP_RESPONSE = "response";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String RESPONSE_TYPE = "responseType";
	public static final String USER_AGENT = "User-Agent"; 
	public static final String USER_AGENT_VALUE = "salesforce-toolkit-android/20";
	
	public static final String PROD_OAUTH_URL="https://login.salesforce.com/services/oauth2/authorize?response_type=token&display=touch&client_id=";
	public static final String SANDBOX_OAUTH_URL="https://test.salesforce.com/services/oauth2/authorize?response_type=token&display=touch&client_id=";
}
