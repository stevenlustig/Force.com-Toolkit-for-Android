package com.sforce.android.soap.partner;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;

import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.sobject.SObject;

public class Salesforce {
	private static Sforce sf;
	private static AsyncSforce asf;
	private static Context sContext;
	private static Integer apiVersion = 20; 
	private static final String TAG = Salesforce.class.getName();
	
	public static void init(Context context){
	  sf=new Sforce(context);
	  asf=new AsyncSforce(sf);
	  SessionStore.restore(sf, context);
	  sContext=context;
  }
  
  // TODO: There should be some error checking here incase init hasn't been called yet!
  // TODO: Should the synchronous Sforce have an HTTP timeout too?
  public static void setTimeout(int timeout)
  {
	  asf.setTimeout(timeout);
  }
  
  public static void describeSObject(String sObject, ResponseListener describeSObjectResponseListener)
  {
		HashMap<String, String> requestFields = new HashMap<String, String>();
		requestFields.put("requestType", "describeSObject");
		requestFields.put("sessionId", sf.getSessionId());
		requestFields.put("client", sf.getClient());
		requestFields.put("sObjectType", sObject);
		requestFields.put("responseType", "describeSObject");
		BaseRequestListener listener = new DescribeSObjectRequestListener();
		listener.setResponseListener(describeSObjectResponseListener);
		asf.request(requestFields, listener);
  }
	
  public static void query(String queryString, ResponseListener queryResponseListener)
  {
	  HashMap<String, String> requestFields=new HashMap<String, String>();
      requestFields.put("requestType", "query");
      requestFields.put("sessionId", sf.getSessionId());
	  requestFields.put("client", sf.getClient());
      requestFields.put("queryString", queryString);
      requestFields.put("responseType", "query");
      BaseRequestListener listener=new QueryRequestListener();
      listener.setResponseListener(queryResponseListener);
  	  asf.request(requestFields, listener);
  }
  
  public static void query(String queryString, Integer batchSize, ResponseListener queryResponseListener)
  {
	  HashMap<String, String> requestFields=new HashMap<String, String>();
      requestFields.put("requestType", "query");
      requestFields.put("sessionId", sf.getSessionId());
	  requestFields.put("client", sf.getClient());
      requestFields.put("queryString", queryString);
      requestFields.put("batchSize", batchSize.toString());
      requestFields.put("responseType", "query");
      BaseRequestListener listener=new QueryRequestListener();
      listener.setResponseListener(queryResponseListener);
  	  asf.request(requestFields, listener);	  
  }

  public static void queryAll(String queryString, ResponseListener queryAllResponseListener)
  {
	HashMap<String, String> requestFields=new HashMap<String, String>();
	requestFields.put("requestType", "queryAll");
	requestFields.put("queryString", queryString);
	requestFields.put("sessionId", sf.getSessionId());
	requestFields.put("client", sf.getClient());
	requestFields.put("responseType", "queryAll");
    BaseRequestListener listener=new QueryAllRequestListener();
    listener.setResponseListener(queryAllResponseListener);
	asf.request(requestFields, listener);
  }

  public static void queryMore(String queryLocator, ResponseListener queryMoreResponseListener)
  {
	HashMap<String, String> requestFields=new HashMap<String, String>();
    requestFields.put("requestType", "queryMore");
    requestFields.put("queryLocator", queryLocator);
    requestFields.put("sessionId", sf.getSessionId());
	requestFields.put("client", sf.getClient());
    requestFields.put("responseType", "queryMore");
    BaseRequestListener listener=new QueryMoreRequestListener();
    listener.setResponseListener(queryMoreResponseListener);
	asf.request(requestFields, listener);
  }
  
  public static void queryMore(String queryLocator, Integer batchSize, ResponseListener queryMoreResponseListener){
		HashMap<String, String> requestFields=new HashMap<String, String>();
	    requestFields.put("requestType", "queryMore");
	    requestFields.put("queryLocator", queryLocator);
	    requestFields.put("batchSize", batchSize.toString());
	    requestFields.put("sessionId", sf.getSessionId());
		requestFields.put("client", sf.getClient());
	    requestFields.put("responseType", "queryMore");
	    BaseRequestListener listener=new QueryMoreRequestListener();
	    listener.setResponseListener(queryMoreResponseListener);
		asf.request(requestFields, listener);	  
  }

  public static void create(ArrayList<SObject> records, ResponseListener createResponseListener)
  {
	HashMap<String, String> sessionFields = new HashMap<String, String>();
	sessionFields.put("requestType", "createSObject");
	sessionFields.put("sessionId", sf.getSessionId());
	sessionFields.put("client", sf.getClient());
	sessionFields.put("responseType", "create");
    BaseRequestListener listener=new CreateRequestListener();
    listener.setResponseListener(createResponseListener);
	asf.request(records, sessionFields, listener);
  }

  public static void retrieve(String[] fieldList, String sObjectType, String[] ids, ResponseListener retrieveResponseListener)
  {
	HashMap<String, String> requestFields = new HashMap<String, String>();
	requestFields.put("requestType", "retrieve");
	requestFields.put("sessionId", sf.getSessionId());
	requestFields.put("client", sf.getClient());
	requestFields.put("responseType", "retrieve");
	
	String fields = fieldList[0];
	for (Integer i = 1; i<fieldList.length; i++)
	{
		fields+= ","+fieldList[i];
	}	
	requestFields.put("fieldList", fields);
	requestFields.put("sObjectType", sObjectType);
	String idString = ids[0];
	for (Integer i = 1; i<ids.length; i++)
	{
		idString+= ","+ids[i];
	}	
	requestFields.put("ids", idString);
    BaseRequestListener listener=new RetrieveRequestListener();
    listener.setResponseListener(retrieveResponseListener);
	asf.request(requestFields, listener);
  }
  public static void delete(String[] deleteIds, ResponseListener deleteResponseListener)
  {
	HashMap<String, String> requestFields=new HashMap<String, String>();
	requestFields.put("requestType", "delete");
	requestFields.put("responseType", "delete");
	requestFields.put("sessionId", sf.getSessionId());
	requestFields.put("client", sf.getClient());
	
	String ids = deleteIds[0];
	for (Integer i = 1; i<deleteIds.length; i++)
	{
		ids+= ","+deleteIds[i];
	}
	requestFields.put("ids", ids);
    BaseRequestListener listener=new DeleteRequestListener();
    listener.setResponseListener(deleteResponseListener);
	asf.request(requestFields, listener);
  }

  public static void update(ArrayList<SObject> records, ResponseListener updateResponseListener)
  {
	HashMap<String, String> sessionFields = new HashMap<String, String>();
	sessionFields.put("requestType", "updateSObject");
	sessionFields.put("sessionId", sf.getSessionId());
	sessionFields.put("client", sf.getClient());
	sessionFields.put("responseType", "update");
    BaseRequestListener listener=new UpdateRequestListener();
    listener.setResponseListener(updateResponseListener);
	asf.request(records, sessionFields, listener);
  }

  public static void upsert(String externalIdField, ArrayList<SObject> records, ResponseListener upsertResponseListener)
  {
	HashMap<String, String> sessionFields = new HashMap<String, String>();
	sessionFields.put("requestType", "upsertSObject");
	sessionFields.put("sessionId", sf.getSessionId());
	sessionFields.put("client", sf.getClient());
	sessionFields.put("responseType", "upsert");
	sessionFields.put("externalIdField", externalIdField);
    BaseRequestListener listener=new UpsertRequestListener();
    listener.setResponseListener(upsertResponseListener);
	asf.request(records, sessionFields, listener);
  }
  
  public static void login(ConnectorConfig parameters, ResponseListener loginResponseListener){
	  StringBuffer endPoint=new StringBuffer();
	  if (parameters.getIsSandbox()){
		  endPoint.append("https://test.salesforce.com/services/Soap/u/").append(parameters.getApiVersion()).append(".0");
	  } else {
		  endPoint.append("https://login.salesforce.com/services/Soap/u/").append(parameters.getApiVersion()).append(".0");
	  }
	  sf.setClient(parameters.getClient());
	  sf.setServerURL(endPoint.toString());
      HashMap<String, String> requestFields=new HashMap<String, String>();
	  requestFields.put("requestType", "login");
	  requestFields.put("endPoint", sf.getEndPoint());
	  requestFields.put("username", parameters.getUsername());
	  requestFields.put("password", parameters.getPassword());
	  requestFields.put("securityToken", parameters.getSecurityToken());
	  requestFields.put("responseType", "login");
	  
	  BaseRequestListener listener=new LoginRequestListener();
	  listener.setResponseListener(loginResponseListener);
	  asf.request(requestFields, listener);
  }

  public static void loginOAuth(Activity activity, OAuthConnectorConfig parameters, ResponseListener oAuthLoginListener){
	  sf.setClient(parameters.getClient());
	  BaseRequestListener listener=new OAuthLoginRequestListener();
	  listener.setResponseListener(oAuthLoginListener);
	  apiVersion = parameters.getApiVersion();
	  asf.loginOAuth(activity, parameters, listener);
  }
  
  public static void logout(ResponseListener logoutResponseListener)
  {
	HashMap<String, String> requestFields=new HashMap<String, String>();
    requestFields.put("requestType", "logout");
    requestFields.put("sessionId", sf.getSessionId());
	requestFields.put("client", sf.getClient());
    requestFields.put("responseType", "logout");
    BaseRequestListener listener=new LogoutRequestListener();
    listener.setResponseListener(logoutResponseListener);
	asf.request(requestFields, listener);
  }
  
  public static class CreateRequestListener extends BaseRequestListener{
      public void onComplete(final Object cresponse) {
			CreateSoapResponse createresponse=(CreateSoapResponse) cresponse;
			final ArrayList<SaveResult> resultArray=createresponse.getResult();
	    	getResponseListener().onComplete(resultArray);
      }        
  }
  

  public static class DescribeSObjectRequestListener extends BaseRequestListener {
	public void onComplete(final Object dresponse) {
		DescribeSObjectSoapResponse response = (DescribeSObjectSoapResponse) dresponse;
		DescribeSObjectResult result = response.getResult();
		ArrayList<SObject> records = result.getRecords();

		getResponseListener().onComplete(records);
	}
  }

  public static class QueryRequestListener extends BaseRequestListener{
      public void onComplete(final Object qresponse) {
    	    QuerySoapResponse queryresponse=(QuerySoapResponse) qresponse;
			QueryResult qresult=queryresponse.getResult();
	    	ArrayList<SObject> records=qresult.getRecords();

	    	getResponseListener().onComplete(records);
      }
  }
	  
  
  public static class QueryWithLocatorRequestListener extends BaseRequestListener{
      public void onComplete(final Object qresponse) {
			QuerySoapResponse queryresponse=(QuerySoapResponse) qresponse;
			QueryResult qresult=queryresponse.getResult();
	    	ArrayList<SObject> records=qresult.getRecords();
	    	String queryLocator=qresult.getQueryLocator();
	    	HashMap<String, Object> qresults=new HashMap<String, Object>();
	    	qresults.put("records", records);
	    	qresults.put("queryLocator", queryLocator);
	    	getResponseListener().onComplete(qresults);
      }
  }
  
  public static class QueryAllRequestListener extends BaseRequestListener{
      public void onComplete(final Object qaresponse) {
			QueryAllSoapResponse queryallresponse=(QueryAllSoapResponse) qaresponse;
			QueryResult qresult=queryallresponse.getResult();
	    	ArrayList<SObject> records=qresult.getRecords();
	    	getResponseListener().onComplete(records);
      }
  }
 
  public static class QueryMoreRequestListener extends BaseRequestListener{
      public void onComplete(final Object qmresponse) {
			QueryMoreSoapResponse querymoreresponse=(QueryMoreSoapResponse) qmresponse;
			final QueryResult qresult=querymoreresponse.getResult();
			ArrayList<SObject> resultArray=qresult.getRecords();
			String queryLocator=qresult.getQueryLocator();
			String size=new Integer(qresult.getSize()).toString();
			String isDone=new Boolean(qresult.isDone()).toString();
			HashMap<String, Object> qmResult=new HashMap<String, Object>();
			qmResult.put("resultArray", resultArray);
			qmResult.put("queryLocator", queryLocator);
			qmResult.put("size", size);
			qmResult.put("isDone", isDone);
			getResponseListener().onComplete(qmResult);
      }
  }
  
  public static class UpdateRequestListener extends BaseRequestListener{
      public void onComplete(final Object uresponse) {
			UpdateSoapResponse updateresponse=(UpdateSoapResponse) uresponse;
			final ArrayList<SaveResult> resultArray=updateresponse.getResult();
	    	getResponseListener().onComplete(resultArray);
      }
  }

  public static class RetrieveRequestListener extends BaseRequestListener{
      public void onComplete(final Object rresponse) {
			RetrieveSoapResponse retrieveresponse=(RetrieveSoapResponse) rresponse;
			ArrayList<SObject> records=retrieveresponse.getResult();
	    	getResponseListener().onComplete(records);
      }
  }

  public static class UpsertRequestListener extends BaseRequestListener{

      public void onComplete(final Object uresponse) {
			UpsertSoapResponse upsertresponse=(UpsertSoapResponse) uresponse;
			ArrayList<UpsertResult> ur=upsertresponse.getResult();
			getResponseListener().onComplete(ur);
      }
  }

  public static class DeleteRequestListener extends BaseRequestListener{
      public void onComplete(final Object dresponse) {
			com.sforce.android.soap.partner.DeleteSoapResponse deleteresponse=(com.sforce.android.soap.partner.DeleteSoapResponse) dresponse;
			final ArrayList<com.sforce.android.soap.partner.DeleteResult> resultArray=deleteresponse.getResult();
			getResponseListener().onComplete(resultArray);
      }        
  }

  private static class LogoutRequestListener extends BaseRequestListener{
      public void onComplete(Object response) {
    	  SessionStore.clear(sf, sContext);
    	  getResponseListener().onComplete(response);
      }
  }    

  public static class LoginRequestListener extends BaseRequestListener{

      public void onComplete(final Object uresponse) {
    	  	LoginResult loginResponse=((LoginSoapResponse) uresponse).getLoginResult();
		    sf.setSessionId(loginResponse.getSessionId());
			sf.setServerURL(loginResponse.getServerURL());
			SessionStore.save(sf, sContext);
			getResponseListener().onComplete(loginResponse);
      }
  }

  public static class OAuthLoginRequestListener extends BaseRequestListener{

      public void onComplete(final Object uresponse) {
    	  	OAuthLoginResult loginResponse=(OAuthLoginResult) uresponse;
		    sf.setSessionId(loginResponse.getAccessToken());
			sf.setServerURL(loginResponse.getInstanceUrl()+"/services/Soap/u/"+apiVersion+".0/");
			SessionStore.save(sf, sContext);
			getResponseListener().onComplete(loginResponse);
      }

  	public void onSforceError(String faultType, Response response) {
  		OAuthFaultResponse error = (OAuthFaultResponse)response;
        getResponseListener().onSforceError(error.getApiFault());
  	}       
  }
 
  public static interface ResponseListener {
      public void onComplete(Object response);
      public void onException(Exception e);
      public void onSforceError(ApiFault apiFault);
  }

}
