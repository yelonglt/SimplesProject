package com.eyetech.mvp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by eyetech on 16/5/27.
 * mail:354734713@qq.com
 */
public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity{

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter =initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attach((V)this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    public abstract T initPresenter();
}
