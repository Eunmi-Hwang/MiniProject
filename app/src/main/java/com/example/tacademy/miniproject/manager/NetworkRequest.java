package com.example.tacademy.miniproject.manager;

import android.view.SurfaceHolder;

import com.example.tacademy.miniproject.data.NetworkResult;
import com.example.tacademy.miniproject.data.UserResult;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Tacademy on 2016-08-09.
 */
public abstract class NetworkRequest<T> implements Callback {
    T result;
    String message;
    Throwable e;
    int code;
    NetworkManager.OnResultListener<T> listener;

    public void setOnNetworkListener(NetworkManager.OnResultListener<T> listener) {
        this.listener = listener;
    }

    public abstract Request getRequest();

    protected abstract T parser(ResponseBody body) throws IOException;

    Call call;

    void process(OkHttpClient client) {
        Request request = getRequest();
        call = client.newCall(request);
        call.enqueue(this);
    }


    @Override
    public void onFailure(Call call, IOException e) {
        sendError(-1, "error", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            try {
                sendSuccess(parser(response.body()));
            } catch (IOException e) {
                sendError(-1, e.getMessage(), e);
            }
            sendError(response.code(), response.message(), null);
        }
    }


    // cancel 만들기


    // isCancel도

    private void sendSuccess(T result) {
        this.result = result;
        NetworkManager.getInstance().sendSuccess(this);
    }

    void sendSuccess() {
        if (listener != null) {
            listener.onSuccess(this, result);
        }
    }

    protected void sendError(int code, String message, Throwable e) {
        this.code = code;
        this.message = message;
        this.e = e;
        NetworkManager.getInstance().sendFail(this);
    }
    void sendFail() {
        if(listener != null) {
            listener.onFail(this, code, message, e);
        }
    }

}
