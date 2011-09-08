package com.sforce.android.soap.partner;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionStore {
	private static final String KEY = "sforce-session";
    private static final String SESSIONID = "sessionId";
    private static final String CLIENT = "client";
    private static final String EXPIRES = "expiresIn";
    private static final String SERVERURL = "serverURL";
    private static final String USERNAME="username";
    private static final String PASSWORD="password";
    private static final String SECURITYTOKEN="securitytoken";
    private static final String ENDPOINT="endpoint";
    private static final String SOAP_SERVER="soapServer";
        
    public static boolean save(Sforce session, Context context) {
        Editor editor =
            context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putString(SESSIONID, session.getSessionId());
        editor.putLong(EXPIRES, session.getSessionExpires());
        editor.putString(SERVERURL, session.getServerURL());
        editor.putString(SOAP_SERVER, session.getSoapServer());
        editor.putString(USERNAME, session.getUserName());
        editor.putString(PASSWORD, session.getPassword());
        editor.putString(SECURITYTOKEN, session.getPassword());
        editor.putString(ENDPOINT, session.getEndPoint());
        editor.putString(CLIENT, session.getClient());
        return editor.commit();
    }

    public static boolean restore(Sforce session, Context context) {
        SharedPreferences savedSession =
            context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        session.setSessionId(savedSession.getString(SESSIONID, null));
        session.setSessionExpires(savedSession.getLong(EXPIRES, 0));
        session.setServerURL(savedSession.getString(SERVERURL, null));
        session.setSoapServer(session.getServerURL());
        session.setUserName(savedSession.getString(USERNAME, null));
        session.setPassword(savedSession.getString(PASSWORD, null));
        session.setSecurityToken(savedSession.getString(SECURITYTOKEN, null));
        session.setEndPoint(savedSession.getString(ENDPOINT, null));
        session.setClient(savedSession.getString(CLIENT, ""));
        return session.isSessionIdValid();
    }

    public static void clear(Sforce session, Context context) {
        Editor editor = 
            context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        session.setServerURL(null);
        session.setSoapServer(null);
        session.setSessionExpires(0);
        session.setSessionId(null);
        session.setEndPoint(null);
        session.setPassword(null);
        session.setSecurityToken(null);
        session.setUserName(null);
        editor.clear();
        editor.commit();
    }
}
