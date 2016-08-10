package com.example.tacademy.miniproject;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.tacademy.miniproject.data.NetworkResult;
import com.example.tacademy.miniproject.data.UserResult;
import com.example.tacademy.miniproject.login.SimpleLoginActivity;
import com.example.tacademy.miniproject.manager.NetworkManager;
import com.example.tacademy.miniproject.manager.NetworkRequest;
import com.example.tacademy.miniproject.manager.PropertyManager;
import com.example.tacademy.miniproject.request.LoginRequest;
import com.example.tacademy.miniproject.request.ProfileRequest;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProfileRequest request = new ProfileRequest(this);
        // ProfileRequest의 request의 객체 만듬

        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<UserResult>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<UserResult>> request, NetworkRequest<UserResult> result) {
                moveMainActivity();
                // 성공하면 MainActivity로 가고

            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<UserResult>> request, int code, String message, Throwable e) {
                if (code == -1) {
                    if (message.equals("not login")) {
                        return;
                    }

                }
                moveLoginActivity();
                // 실패하면 Login창으로 가기
            }

        });
    }

    private void loginSharedPreference() {
        if (isAutoLogin()) {
            provessAutoLogin();
        } else {
            moveLoginActivity();
        }
    }


    public boolean isAutoLogin() {
        String email = PropertyManager.getInstance().getEmail();
        return !TextUtils.isEmpty(email);
    }

    public void provessAutoLogin() {
        String email = PropertyManager.getInstance().getEmail();
        if (!TextUtils.isEmpty(email)) {
            String password = PropertyManager.getInstance().getPassword();
            String regId = PropertyManager.getInstance().getRegisterId();

            LoginRequest request = new LoginRequest(this, email, password, regId);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<UserResult>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<UserResult>> request, NetworkResult<UserResult> result) {
                    moveMainActivity();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<UserResult>> request, int code, String message, Throwable e) {

                }
            });
        }
    }

    private void moveMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void moveLoginActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, SimpleLoginActivity.class));
                finish();
            }
        }, 1000);
    }

    Handler mHandler = new Handler(Looper.getMainLooper());
}
