package com.eyetech.mvp;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.eyetech.mvp.fragment.LoginFragment;

/**
 * Created by eyetech on 16/5/27.
 * mail:354734713@qq.com
 */
public class FLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flogin);

        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.ss_container, LoginFragment.newInstance(), "LoginFragment")
                .addToBackStack("LoginFragment").commitAllowingStateLoss();


    }
}
