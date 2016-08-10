package com.example.tacademy.miniproject.request;

import android.content.Context;

import com.example.tacademy.miniproject.data.NetworkResult;
import com.example.tacademy.miniproject.data.UserResult;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-09.
 */
public class LoginRequest extends AbstractRequest<NetworkResult<UserResult>> {
    Request request;
    public LoginRequest(Context context, String email, String password, String regId) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("signin")
                .build();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("regId", regId)
                .build();

        request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<UserResult>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
