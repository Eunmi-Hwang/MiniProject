package com.example.tacademy.miniproject.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tacademy.miniproject.MainActivity;
import com.example.tacademy.miniproject.R;

public class SimpleLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_login);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SignUpFragment())
                    .commit();
        }
    }
    public void changeSingup() {
                getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new SignUpFragment())
                                .addToBackStack(null)
                                .commit();
            }

    public void moveMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
