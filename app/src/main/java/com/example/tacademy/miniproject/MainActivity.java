package com.example.tacademy.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tacademy.miniproject.data.NetworkResult;
import com.example.tacademy.miniproject.data.UserResult;
import com.example.tacademy.miniproject.login.SimpleLoginActivity;
import com.example.tacademy.miniproject.manager.NetworkManager;
import com.example.tacademy.miniproject.manager.NetworkRequest;
import com.example.tacademy.miniproject.manager.PropertyManager;
import com.example.tacademy.miniproject.request.FriendListRequest;
import com.example.tacademy.miniproject.request.LogoutRequest;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<UserResult> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<UserResult>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);

        FriendListRequest request = new FriendListRequest(this);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<UserResult>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<UserResult>>> request, NetworkResult<List<UserResult>> result) {
                List<UserResult> users = result.getResult();
                mAdapter.addAll(users);
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<UserResult>>> request, int code, String message, Throwable e) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_logout){
            LogoutRequest request = new LogoutRequest(this);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<String>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<String>> request, NetworkResult<String> result) {
                    PropertyManager.getInstance().setEmail("");
                    PropertyManager.getInstance().setPassword("");
                    Intent intent = new Intent(MainActivity.this, SimpleLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    // Activity는 Task 위에서 구동됨
                    // 여기는 지금 로그아웃하고 나서 다시 로그인 창 떠야되는 곳이니까
                    // 지금까지 쌓였던 액티비티들 다 지워버리고 새로운 액티비티 창 하나 뜨게 해주는 것임
                    // 저 Intent.addFlags저거
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<String>> request, int code, String message, Throwable e) {

                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}
