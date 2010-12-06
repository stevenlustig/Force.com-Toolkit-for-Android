package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class LoginSoapResponse implements Response{
	private static final String RESULT = "result";
	private static final String METADATA_SERVER_URL = "metadataServerUrl";
	private static final String PASSWORD_EXPIRED = "passwordExpired";
	private static final String SANDBOX ="sandbox";
	private static final String USER_INFO = "userInfo";
	private static final String SERVER_URL = "serverUrl";
	private static final String SESSION_ID = "sessionId";
	private static final String ACCESSIBILITY_MODE = "accessibilityMode";
	private static final String CURRENCY_SYMBOL = "currencySymbol";
	private static final String ORG_DEFAULT_CURRENCY_ISO_CODE="orgDefaultCurrencyIsoCode";
	private static final String ORG_DISALLOW_HTML_ATTACHMENTS = "orgDisallowHtmlAttachments";
	private static final String ORG_HAS_PERSON_ACCOUNTS = "orgHasPersonAccounts";
	private static final String ORGANIZATION_ID = "organizationId";
	private static final String ORGANIZATION_MULTI_CURRENCY = "organizationMultiCurrency";
	private static final String ORGANIZATION_NAME = "organizationName";
	private static final String PROFILE_ID = "profileId";
	private static final String ROLE_ID = "roleId";
	private static final String USER_DEFAULT_CURRENCY_ISO_CODE = "userDefaultCurrencyIsoCode";
	private static final String USER_EMAIL = "userEmail";
	private static final String USER_FULL_NAME = "userFullName";
	private static final String USER_ID = "userId";
	private static final String USER_LANGUAGE = "userLanguage";
	private static final String USER_LOCALE = "userLocale";
	private static final String USER_NAME = "userName";
	private static final String USER_TIME_ZONE = "userTimeZone";
	private static final String USER_TYPE = "userType";
	private static final String USER_UI_SKIN = "userUiSkin";
	static final String LOGIN_RESPONSE="loginResponse";
	
	private LoginResult lr=null;
	
	public LoginSoapResponse(){
	}
	

	public LoginSoapResponse(String responseXML){
		try{
			UserInfo userInfo=null;
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(responseXML));
			int eventType = xpp.getEventType();
			boolean done = false;
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				switch (eventType){
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						name = xpp.getName();
						if (name.equalsIgnoreCase(RESULT)){
							lr=new LoginResult();
						} else if (name.equalsIgnoreCase(METADATA_SERVER_URL)){
							lr.setMetadataServerUrl(xpp.nextText());
						} else if (name.equalsIgnoreCase(PASSWORD_EXPIRED)){
							lr.setPasswordExpired(Boolean.parseBoolean(xpp.nextText()));
						} else if (name.equalsIgnoreCase(SANDBOX)){
							lr.setSandbox(Boolean.parseBoolean(xpp.nextText()));
						} else if (name.equalsIgnoreCase(SERVER_URL)){
							lr.setServerURL(xpp.nextText());
						} else if (name.equalsIgnoreCase(SESSION_ID)){
							lr.setSessionId(xpp.nextText());
						} else if (name.equalsIgnoreCase(USER_ID)){
							lr.setUserId(xpp.nextText());
							if (userInfo !=null){
								userInfo.setUserId(lr.getUserId());
							}
						} else if (name.equalsIgnoreCase(USER_INFO)){
							userInfo=lr.getUserInfo();
						} else if (name.equalsIgnoreCase(ACCESSIBILITY_MODE)){
							userInfo.setAccessibilityMode(Boolean.parseBoolean(xpp.nextText()));
						} else if (name.equalsIgnoreCase(CURRENCY_SYMBOL)){
							userInfo.setCurrencySymbol(xpp.nextText());
						} else if (name.equalsIgnoreCase(ORG_DEFAULT_CURRENCY_ISO_CODE)){
							userInfo.setOrgDefaultCurrencyIsoCode(xpp.nextText());
						} else if (name.equalsIgnoreCase(ORG_DISALLOW_HTML_ATTACHMENTS)){
							userInfo.setOrgDisallowHtmlAttachments(Boolean.parseBoolean(xpp.nextText()));
						} else if (name.equalsIgnoreCase(ORG_HAS_PERSON_ACCOUNTS)){
							userInfo.setOrgHasPersonAccounts(Boolean.parseBoolean(xpp.nextText()));
						} else if (name.equalsIgnoreCase(ORGANIZATION_ID)){
							userInfo.setOrganizationId(xpp.nextText());
						} else if (name.equalsIgnoreCase(ORGANIZATION_MULTI_CURRENCY)){
							userInfo.setOrganizationMultiCurrency(Boolean.parseBoolean(xpp.nextText()));
						} else if (name.equalsIgnoreCase(ORGANIZATION_NAME)){
							userInfo.setOrganizationName(xpp.nextText());
						} else if (name.equalsIgnoreCase(PROFILE_ID)){
							userInfo.setProfileId(xpp.nextText());
						} else if (name.equalsIgnoreCase(ROLE_ID)){
							userInfo.setRoleId(xpp.nextText());
						} else if (name.equalsIgnoreCase(USER_DEFAULT_CURRENCY_ISO_CODE)){
							userInfo.setUserDefaultCurrencyIsoCode(xpp.nextText());
						} else if (name.equalsIgnoreCase(USER_EMAIL)){
							userInfo.setUserEmail(xpp.nextText());
						} else if (name.equalsIgnoreCase(USER_FULL_NAME)){
							userInfo.setUserFullName(xpp.nextText());
						} else if (name.equalsIgnoreCase(USER_LANGUAGE)){
							userInfo.setUserLanguage(xpp.nextText());
						} else if (name.equalsIgnoreCase(USER_LOCALE)){
							userInfo.setUserLocale(xpp.nextText());
						} else if (name.equalsIgnoreCase(USER_NAME)){
							userInfo.setUserName(xpp.nextText());
						} else if (name.equalsIgnoreCase(USER_TIME_ZONE)){
							userInfo.setUserTimeZone(xpp.nextText());
						} else if (name.equalsIgnoreCase(USER_UI_SKIN)){
							userInfo.setUserUiSkin(xpp.nextText());
						} else if (name.equalsIgnoreCase(USER_TYPE)){
							userInfo.setUserType(xpp.nextText());
						}  
						break;
					case XmlPullParser.END_TAG:
						name = xpp.getName();
						if (name.equalsIgnoreCase(LOGIN_RESPONSE)){
							done = true;
						}
						break;
				}
				eventType = xpp.next();
			}
		}catch(XmlPullParserException xppe){
			xppe.printStackTrace();
			throw new RuntimeException(xppe);
		} catch(IOException ioe){
			ioe.printStackTrace();
			throw new RuntimeException(ioe);
		}
	}
	
	public LoginResult getLoginResult(){
		return this.lr;
	}
	
	public void setLoginResult(LoginResult lr){
		this.lr=lr;
	}
	
	public Response getSoapResponse(){
		return this;
	}
}
