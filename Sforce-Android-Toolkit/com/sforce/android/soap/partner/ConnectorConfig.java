package com.sforce.android.soap.partner;

public class ConnectorConfig {

    private String username = null;
    private String password = null;
    private String client = "";
    private String securitytoken = null;
    private Integer apiVersion = 20;
	private boolean isSandbox = false;
	
	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String uName)
	{
		this.username = uName;
	}

	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String pwd)
	{
		this.password = pwd;
	}
	
	public String getClient() 
	{
		return this.client;
	}
	
	public void setClient(String client) 
	{
		this.client = client;
	}

	public String getSecurityToken()
	{
		return this.securitytoken;
	}

	public void setSecurityToken(String token)
	{
		this.securitytoken = token;
	}

	public Integer getApiVersion()
	{
		return this.apiVersion;
	}

	public void setApiVersion(Integer version)
	{
		this.apiVersion = version;
	}

	public Boolean getIsSandbox()
	{
		return this.isSandbox;
	}

	public void setIsSandbox(boolean sand)
	{
		this.isSandbox = sand;
	}
	
	public ConnectorConfig(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public ConnectorConfig(String username, String password, String securityToken)
	{
		this.username = username;
		this.password = password;
		this.securitytoken = securityToken;
	}
}
