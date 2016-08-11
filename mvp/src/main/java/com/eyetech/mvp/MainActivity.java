package com.eyetech.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eyetech.mvp.bean.User;
import com.eyetech.mvp.presenter.UserLoginPresenter;
import com.eyetech.mvp.view.IUserLoginView;

public class MainActivity extends AppCompatActivity implements IUserLoginView {

    private EditText et_username;
    private EditText et_password;

    private Button btn_login;
    private Button btn_cancel;

    private ProgressBar pb_loading;

    UserLoginPresenter presenter = new UserLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login();
            }
        });

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cancel();
            }
        });

    }


    @Override
    public String getUserName() {
        if (TextUtils.isEmpty(et_username.getText())) {
            Toast.makeText(this, "please input username", Toast.LENGTH_SHORT).show();
        }
        return et_username.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        if (TextUtils.isEmpty(et_password.getText())) {
            Toast.makeText(this, "please input username", Toast.LENGTH_SHORT).show();
        }
        return et_password.getText().toString().trim();
    }

    @Override
    public void showLoading() {
        pb_loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pb_loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoginSuccess(User user) {
        Toast.makeText(this, "login success==" + user.getUsername(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginFailed() {
        Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearUserName() {
        et_username.setText("");
    }

    @Override
    public void clearPassword() {
        et_password.setText("");
    }
}
