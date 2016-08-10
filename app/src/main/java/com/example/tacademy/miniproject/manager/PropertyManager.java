package com.example.tacademy.miniproject.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.tacademy.miniproject.MyApplication;

/**
 * Created by Tacademy on 2016-08-10.
 */
public class PropertyManager {
    private static PropertyManager instance;

    public static PropertyManager getInstance() {
        if(instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefers;
    SharedPreferences.Editor mEditors;

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REGISTERId = "1234";
    private PropertyManager() {
        Context context = MyApplication.getContext();
        mPrefers = PreferenceManager.getDefaultSharedPreferences(context);
        mEditors = mPrefers.edit();
    }

    public void setEmail(String email) {
        mEditors.putString(KEY_EMAIL, email);
        mEditors.commit();
    }

    public void setPassword(String password) {
        mEditors.putString(KEY_PASSWORD, password);
        mEditors.commit();
    }

    public void setRegisterId(String registerId) {
        mEditors.putString(KEY_REGISTERId, registerId);
        mEditors.commit();
    }

    public String getEmail() {
        return mPrefers.getString(KEY_EMAIL, "");
    }

    public String getPassword() {
        return mPrefers.getString(KEY_PASSWORD, "");
    }

    public String getRegisterId() {
        return mPrefers.getString(KEY_REGISTERId, "");
    }
}
