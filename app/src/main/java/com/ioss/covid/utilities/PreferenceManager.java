package com.ioss.covid.utilities;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class PreferenceManager {

    private final String PREF_NAME = "pref_name?";
    private final String PREF_TOKEN = "pref_token?";
    private final String PRE_USER_TYPE = "pref_user_type?";
    private final String PREF_SELECTED_LANGUAGE = "pref_selected_lang";

    private SharedPreferences preferences;
    private SharedPreferences staticPreferences;
    private SharedPreferences.Editor staticEditor;
    private SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {

        preferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        staticPreferences = context.getSharedPreferences("pref_staic_prefname?", Context.MODE_PRIVATE);
        editor = preferences.edit();
        staticEditor = staticPreferences.edit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

    public void setSelectedLanguage(String code) {
        editor.putString(PREF_SELECTED_LANGUAGE, code);
        editor.commit();
    }

    public String getSelectedLanguage() {
        return preferences.getString(PREF_SELECTED_LANGUAGE, "en");
    }

    public void setToken(String token) {
        editor.putString(PREF_TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        return preferences.getString(PREF_TOKEN, "");
    }

/*    public String getAuthToken(){
//        if(isUserLogged()){
            return getToken();
//        }
//        return Constants.STATIC_KEY;
    }*/

    public Boolean isUserLogged() {
        return getToken().length() != 0;
    }


    public void setName(String name) {
        editor.putString(PREF_NAME, name);
        editor.commit();
    }

    public String getName() {
        return preferences.getString(PREF_NAME, "");
    }


    public void setUserType(String name) {
        editor.putString(PRE_USER_TYPE, name);
        editor.commit();
    }

    public String getUserType() {
        return preferences.getString(PRE_USER_TYPE, "LOCAL");
    }

    public HashMap<String, String> getBaseParams() {
        HashMap<String,String> param=new HashMap<>();
        param.put("lang","en");
//        param.put("from_mobile","true");
        return param;
    }
}
