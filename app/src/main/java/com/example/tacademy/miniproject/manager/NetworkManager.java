package com.example.tacademy.miniproject.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.tacademy.miniproject.MyApplication;
import com.example.tacademy.miniproject.data.NetworkResult;
import com.example.tacademy.miniproject.data.UserResult;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Tacademy on 2016-08-09.
 */
public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    OkHttpClient client;

    private NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        builder.cookieJar(cookieJar);

        File cachejar = new File(context.getCacheDir(),"network" );
        if(!cachejar.exists()) {
            cachejar.mkdir();
        }

        Cache cache = new Cache(cachejar, 10*1024*1024);
        builder.cache(cache);

        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);

        client = builder.build();

    }

    public OkHttpClient getClient() {
        return client;
    }

    private static final int MESSAGE_SUCCESS = 1;
    private static final int MESSAGE_FAIL = 2;

    Handler mhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NetworkRequest<?> request = (NetworkRequest<?>) msg.obj;
            switch (msg.what){
                case MESSAGE_SUCCESS:
                    request.sendSuccess();
                    break;
                case MESSAGE_FAIL:
                    request.sendFail();
                    break;
            }
        }
    };

    public interface OnResultListener<T> {
        public void onSuccess(NetworkRequest<T> request, T result);
        public void onFail(NetworkRequest<T> request, int code, String message, Throwable e);
    }

    void sendSuccess(NetworkRequest<?> request){
        Message msg = mhandler.obtainMessage(MESSAGE_SUCCESS, request);
        mhandler.sendMessage(msg);
    }
    void sendFail(NetworkRequest<?> request){
        Message msg = mhandler.obtainMessage(MESSAGE_SUCCESS, request);
        mhandler.sendMessage(msg);
    }

    public <T> void getNetworkData(NetworkRequest<T> request, OnResultListener<T> listener) {
        request.setOnNetworkListener(listener);
        request.process(client);
    }

    // public void cancel도 만들기
}
