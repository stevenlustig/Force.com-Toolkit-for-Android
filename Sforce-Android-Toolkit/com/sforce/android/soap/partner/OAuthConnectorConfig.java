package com.sforce.android.soap.partner;

public class OAuthConnectorConfig {

    private String consumerKey = null;
    private String callbackURL = null;
	private boolean isSandbox = false;
    private Integer apiVersion = 20;

	public String getConsumerKey()
	{
		return this.consumerKey;
	}

	public void setConsumerKey(String key)
	{
		this.consumerKey = key;
	}
	
	public String getCallbackURL()
	{
		return this.callbackURL;
	}

	public void setCallbackURL(String url)
	{
		this.callbackURL = url;
	}

	public Boolean getIsSandbox()
	{
		return this.isSandbox;
	}

	public void setIsSandbox(boolean sand)
	{
		this.isSandbox = sand;
	}
	
	public OAuthConnectorConfig(String cKey, String callbackUrl)
	{
		this.consumerKey = cKey;
		this.callbackURL = callbackUrl;
	}

	public Integer getApiVersion()
	{
		return this.apiVersion;
	}

	public void setApiVersion(Integer apiVer)
	{
		this.apiVersion = apiVer;
	}
}
