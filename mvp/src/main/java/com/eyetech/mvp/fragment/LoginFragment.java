package com.eyetech.mvp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eyetech.mvp.R;
import com.eyetech.mvp.base.BaseFragment;
import com.eyetech.mvp.bean.User;
import com.eyetech.mvp.presenter.LoginPresenter;
import com.eyetech.mvp.view.LoginView;

/**
 * Created by eyetech on 16/5/27.
 * mail:354734713@qq.com
 */
public class LoginFragment extends BaseFragment<LoginView, LoginPresenter> implements LoginView {

    private EditText et_username;
    private EditText et_password;

    private ProgressBar pb_loading;


    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_password = (EditText) view.findViewById(R.id.et_password);

        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);

        Button btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
            }
        });

        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cancel();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoginSuccess(User user) {
        Toast.makeText(getActivity(), "login success==" + user.getUsername(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginFailed() {
        Toast.makeText(getActivity(), "login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUserName() {
        if (TextUtils.isEmpty(et_username.getText())) {
            Toast.makeText(getActivity(), "please input username", Toast.LENGTH_SHORT).show();
        }
        return et_username.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        if (TextUtils.isEmpty(et_password.getText())) {
            Toast.makeText(getActivity(), "please input username", Toast.LENGTH_SHORT).show();
        }
        return et_password.getText().toString().trim();
    }

    @Override
    public void clearUserName() {
        et_username.setText("");
    }

    @Override
    public void clearPassword() {
        et_password.setText("");
    }

    @Override
    public void showLoading() {
        pb_loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pb_loading.setVisibility(View.GONE);
    }
}
