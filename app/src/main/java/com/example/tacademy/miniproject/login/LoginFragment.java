package com.example.tacademy.miniproject.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacademy.miniproject.R;
import com.example.tacademy.miniproject.data.NetworkResult;
import com.example.tacademy.miniproject.data.UserResult;
import com.example.tacademy.miniproject.manager.NetworkManager;
import com.example.tacademy.miniproject.manager.NetworkRequest;
import com.example.tacademy.miniproject.manager.PropertyManager;
import com.example.tacademy.miniproject.request.LoginRequest;
import com.example.tacademy.miniproject.request.SignUpRequest;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
//    @BindView(R.id.btn_edit_email)
//    EditText emailText;
//
//    @BindView(R.id.btn_edit_pass)
//    EditText passText;
//
    EditText emailView;
    EditText passwordView;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailView = (EditText)view.findViewById(R.id.edit_email);
        passwordView = (EditText)view.findViewById(R.id.edit_password);

        Button button = (Button)view.findViewById(R.id.btn_login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailView.getText().toString();
        final String pass = passwordView.getText().toString();
        LoginRequest request = new LoginRequest(getContext(), email, pass, "1234");
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<UserResult>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<UserResult>> request, NetworkResult<UserResult> result) {
                UserResult user = result.getResult();
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                PropertyManager.getInstance().setEmail(email);
                PropertyManager.getInstance().setPassword(pass);
                PropertyManager.getInstance().setRegisterId("1234");
                ((SimpleLoginActivity) getActivity()).moveMainActivity();
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<UserResult>> request, int code, String message, Throwable e) {
                Toast.makeText(getContext(), "fail" + message, Toast.LENGTH_SHORT).show();
            }
        });


                }

        });

        Button button1 = (Button)view.findViewById(R.id.btn_sign_up);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SimpleLoginActivity)getActivity()).changeSingup();
            }
        });
        return view;


    }

//    @OnClick(R.id.btn_btn)
//    public void onLogin(View view) {
//        final String email = emailText.getText().toString();
//        final String pass = passText.getText().toString();
//        LoginRequest request = new LoginRequest(getContext(), email, pass, "1234");
//
//
//        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<UserResult>>() {
//            @Override
//            public void onSuccess(NetworkRequest<NetworkResult<UserResult>> request, NetworkResult<UserResult> result) {
//                UserResult user = result.getResult();
//                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
//                PropertyManager.getInstance().setEmail(email);
//                PropertyManager.getInstance().setPassword(pass);
//                PropertyManager.getInstance().setRegisterId("1234");
//                ((SimpleLoginActivity) getActivity()).moveMainActivity();
//            }
//
//            @Override
//            public void onFail(NetworkRequest<NetworkResult<UserResult>> request, int code, String message, Throwable e) {
//                Toast.makeText(getContext(), "fail" + message, Toast.LENGTH_SHORT).show();
//            }
//        });

//    @OnClick(R.id.btn_signup)
//    public void onSignUp() {
//        ((SimpleLoginActivity)getActivity()).changeSingup();
//    }

    }




