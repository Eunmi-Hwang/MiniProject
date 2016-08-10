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
import com.example.tacademy.miniproject.request.SignUpRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    EditText emailText, passText, nameText;

    public SignUpFragment() {
        // Required empty public constructor
    }

//    @BindView(R.id.btn_sign_up)
//    Button button;
//    @BindView(R.id.edit_useremail)
//    EditText emailText;
//    @BindView(R.id.edit_userpassword)
//    EditText passText;
//    @BindView(R.id.edit_username)
//    EditText nameText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        emailText = (EditText)view.findViewById(R.id.edit_useremail);
        passText = (EditText)view.findViewById(R.id.edit_userpassword);
        nameText = (EditText)view.findViewById(R.id.edit_username);

        Button button = (Button)view.findViewById(R.id.btn_sign_up);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = nameText.getText().toString();
                final String userEmail = emailText.getText().toString();
                final String userPass = passText.getText().toString();
                SignUpRequest request = new SignUpRequest(getContext(), userName, userPass, userEmail, "1234");
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<UserResult>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<UserResult>> request, NetworkResult<UserResult> result) {
                        UserResult user = result.getResult();
                        PropertyManager.getInstance().setEmail(userEmail);
                        PropertyManager.getInstance().setPassword(userPass);
                        PropertyManager.getInstance().setRegisterId("1234");
                        Toast.makeText(getContext(), "success"+user.getId(), Toast.LENGTH_SHORT).show();
                        ((SimpleLoginActivity) getActivity()).moveMainActivity();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<UserResult>> request, int code, String message, Throwable e) {
                        Toast.makeText(getContext(), "fail" + message, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        //ButterKnife.bind(this, view);
        return view;
    }
//        @OnClick(R.id.btn_sign_up)
//            public void onCreate() {
//                String userName = nameText.getText().toString();
//                final String userEmail = emailText.getText().toString();
//                final String userPass = passText.getText().toString();
//                SignUpRequest signUpRequest = new SignUpRequest(getContext(), userName, userPass, userEmail, "1234");
//                NetworkManager.getInstance().getNetworkData(signUpRequest, new NetworkManager.OnResultListener<NetworkResult<UserResult>>() {
//                    @Override
//                    public void onSuccess(NetworkRequest<NetworkResult<UserResult>> request, NetworkResult<UserResult> result) {
//                        UserResult user = result.getResult();
//                        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
//                        PropertyManager.getInstance().setEmail(userEmail);
//                        PropertyManager.getInstance().setPassword(userPass);
//                        PropertyManager.getInstance().setRegisterId("1234");
//                        ((SimpleLoginActivity) getActivity()).moveMainActivity();
//                    }
//
//                    @Override
//                    public void onFail(NetworkRequest<NetworkResult<UserResult>> request, int code, String message, Throwable e) {
//                        Toast.makeText(getContext(), "fail" + message, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }

  }


