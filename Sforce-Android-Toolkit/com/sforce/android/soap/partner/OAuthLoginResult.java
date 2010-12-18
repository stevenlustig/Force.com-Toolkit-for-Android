package com.sforce.android.soap.partner;

import java.util.Calendar;

public class OAuthLoginResult {
	private String _access_token;
	private String _refresh_token;
	private String _instance_url;
	private String _id;
	private Calendar _issued_at;
	private String _signature;
	public String getAccessToken() {
		return _access_token;
	}
	public void setAccessToken(String _access_token) {
		this._access_token = _access_token;
	}
	public String getRefreshToken() {
		return _refresh_token;
	}
	public void setRefreshToken(String _refresh_token) {
		this._refresh_token = _refresh_token;
	}
	public String getInstanceUrl() {
		return _instance_url;
	}
	public void setInstanceUrl(String _instance_url) {
		this._instance_url = _instance_url;
	}
	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
	public Calendar getIssuedAt() {
		return _issued_at;
	}
	public void setIssuedAt(Long issued_at) {
		this._issued_at = Calendar.getInstance();
		this._issued_at.setTimeInMillis(issued_at);
	}
	public String getSignature() {
		return _signature;
	}
	public void setSignature(String _signature) {
		this._signature = _signature;
	}

	public String getUserId() {
		return this._id.substring(_id.lastIndexOf("%2F") + 3);
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("Login Result Details:").append("\n").append("Access Token:").append(_access_token).append("\n");
		sb.append("Instance URL:").append(_instance_url).append("\n");
		sb.append("User Id:").append(getUserId()).append("\n").append("*************************").append("\n");
		return sb.toString();
	}
}
