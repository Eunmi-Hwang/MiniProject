package com.example.tacademy.miniproject.request;

import android.content.Context;

import com.example.tacademy.miniproject.data.NetworkResult;
import com.example.tacademy.miniproject.data.NetworkResultTemp;
import com.example.tacademy.miniproject.data.UserResult;
import com.example.tacademy.miniproject.manager.NetworkRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Tacademy on 2016-08-09.
 */
public class SignUpRequest extends AbstractRequest<NetworkResult<UserResult>> {
    Request request;

    public SignUpRequest(Context context, String username, String password, String email, String registrationId) {
        HttpUrl.Builder builder = getBaseUrlBuilder();
        builder.addPathSegment("signup");

        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("email", email)
                .add("registrationId", registrationId)
                .build();

        request = new Request.Builder()
                .url(builder.build())
                .post(body)
                .tag(context)
                .build();
    }

    @Override
    public Request getRequest() {
        return request;
    }


        protected Type getType() {
        return new TypeToken<NetworkResult<UserResult>>(){}.getType();
    }
    }
