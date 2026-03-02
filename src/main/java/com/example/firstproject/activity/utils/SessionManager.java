package com.example.firstproject.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        prefs = context.getSharedPreferences("UserSession",Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLogin(boolean value){
        editor.putBoolean("isLogin",value);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return prefs.getBoolean("isLogin",false);
    }

    public void logout(){
        editor.clear();
        editor.apply();
    }
}
