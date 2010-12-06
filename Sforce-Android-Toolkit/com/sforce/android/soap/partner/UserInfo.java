package com.sforce.android.soap.partner;

public class UserInfo {
	private boolean accessibilityMode;
	private String currencySymbol;
	private String orgDefaultCurrencyIsoCode;
	private boolean orgDisallowHtmlAttachments;
	private boolean orgHasPersonAccounts;
	private String organizationId;
	private boolean organizationMultiCurrency;
	private String organizationName;
	private String profileId;
	private String roleId;
	private String userDefaultCurrencyIsoCode;
	private String userEmail;
	private String userFullName;
	private String userId;
	private String userLanguage;
	private String userLocale;
	private String userName;
	private String userTimeZone;
	private String userType;
	private String userUiSkin;
	
	public boolean getAccessibilityMode() {
		return accessibilityMode;
	}
	public void setAccessibilityMode(boolean accessibilityMode) {
		this.accessibilityMode = accessibilityMode;
	}
	public String getCurrencySymbol() {
		return currencySymbol;
	}
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
	public String getOrgDefaultCurrencyIsoCode() {
		return orgDefaultCurrencyIsoCode;
	}
	public void setOrgDefaultCurrencyIsoCode(String orgDefaultCurrencyIsoCode) {
		this.orgDefaultCurrencyIsoCode = orgDefaultCurrencyIsoCode;
	}
	public boolean getOrgDisallowHtmlAttachments() {
		return orgDisallowHtmlAttachments;
	}
	public void setOrgDisallowHtmlAttachments(boolean orgDisallowHtmlAttachments) {
		this.orgDisallowHtmlAttachments = orgDisallowHtmlAttachments;
	}
	public boolean getOrgHasPersonAccounts() {
		return orgHasPersonAccounts;
	}
	public void setOrgHasPersonAccounts(boolean orgHasPersonAccounts) {
		this.orgHasPersonAccounts = orgHasPersonAccounts;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public boolean getOrganizationMultiCurrency() {
		return organizationMultiCurrency;
	}
	public void setOrganizationMultiCurrency(boolean organizationMultiCurrency) {
		this.organizationMultiCurrency = organizationMultiCurrency;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserDefaultCurrencyIsoCode() {
		return userDefaultCurrencyIsoCode;
	}
	public void setUserDefaultCurrencyIsoCode(String userDefaultCurrencyIsoCode) {
		this.userDefaultCurrencyIsoCode = userDefaultCurrencyIsoCode;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserLanguage() {
		return userLanguage;
	}
	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}
	public String getUserLocale() {
		return userLocale;
	}
	public void setUserLocale(String userLocale) {
		this.userLocale = userLocale;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserTimeZone() {
		return userTimeZone;
	}
	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserUiSkin() {
		return userUiSkin;
	}
	public void setUserUiSkin(String userUiSkin) {
		this.userUiSkin = userUiSkin;
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("User Info Details:\n").append("Accessibility Mode:").append(accessibilityMode).append("\n");
		sb.append("Currency Symbol:").append(currencySymbol).append("\n").append("Org Default Currency ISO Code:").append(orgDefaultCurrencyIsoCode).append("\n");
		sb.append("Org Disallow Html Attachments:").append(orgDisallowHtmlAttachments).append("\n").append("Org Has Person Accounts:").append(orgHasPersonAccounts).append("\n");
		sb.append("Organization Id:").append(organizationId).append("\n").append("Organization Multi Currency:").append(organizationMultiCurrency).append("\n");
		sb.append("Organization Name:").append(organizationName).append("\n").append("Profile Id:").append(profileId).append("\n");
		sb.append("Currency Symbol:").append(currencySymbol).append("\n").append("Org Default Currency ISO Code:").append(orgDefaultCurrencyIsoCode).append("\n");
		sb.append("Role Id:").append(roleId).append("\n").append("User Default Currency ISO Code:").append(userDefaultCurrencyIsoCode).append("\n");
		sb.append("User Email:").append(userEmail).append("\n").append("User Full Name:").append(userFullName).append("\n");
		sb.append("User Id:").append(userId).append("\n").append("User Language:").append(userLanguage).append("\n");
		sb.append("User Locale:").append(userLocale).append("\n").append("User Name:").append(userName).append("\n");
		sb.append("User Time Zone:").append(userTimeZone).append("\n").append("userType:").append(userType).append("\n");
		sb.append("userUiSkin:").append(userUiSkin).append("\n").append("**************************").append("\n");
		return sb.toString();
	}
}
