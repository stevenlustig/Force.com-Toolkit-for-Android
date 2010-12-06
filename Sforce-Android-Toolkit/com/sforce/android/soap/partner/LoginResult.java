package com.sforce.android.soap.partner;


public class LoginResult {
	private String metadataServerUrl;
	private boolean passwordExpired;
	private boolean sandbox;
	private String serverURL;
	private String sessionId;
	private String userId;
	private UserInfo userInfo=new UserInfo();
	
	public LoginResult(){	
	}
	public String getMetadataServerUrl() {
		return metadataServerUrl;
	}

	public void setMetadataServerUrl(String metadataServerUrl) {
		this.metadataServerUrl = metadataServerUrl;
	}

	public boolean getPasswordExpired() {
		return passwordExpired;
	}

	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}

	public boolean getSandbox() {
		return sandbox;
	}

	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}
	
	public String getServerURL(){
		return this.serverURL;
	}
	
	public void setServerURL(String serverURL){
		this.serverURL=serverURL;
	}
	public String getSessionId(){
		return this.sessionId;
	}
	public void setSessionId(String sessionId){
		this.sessionId=sessionId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("Login Result Details:").append("\n").append("Metadata Server URL:").append(metadataServerUrl).append("\n");
		sb.append("Password Expired:").append(passwordExpired).append("\n").append("Sandbox:").append(sandbox).append("\n");
		sb.append("Server URL:").append(serverURL).append("\n").append("SessionId:").append(sessionId).append("\n");
		sb.append("User Id:").append(userId).append("\n").append("*************************").append("\n").append(userInfo.toString());
		return sb.toString();
	}
}
