package com.example.tacademy.miniproject.request;

import android.content.Context;

import com.example.tacademy.miniproject.data.NetworkResult;
import com.example.tacademy.miniproject.data.UserResult;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-09.
 */
public class FriendListRequest extends AbstractRequest<NetworkResult<List<UserResult>>>{
    Request request;

    public FriendListRequest(Context context) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("friendList")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }



    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<List<UserResult>>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
