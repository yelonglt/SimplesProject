package com.eyetech.mvp.presenter;

import android.os.Handler;
import android.os.Looper;

import com.eyetech.mvp.base.BasePresenter;
import com.eyetech.mvp.bean.User;
import com.eyetech.mvp.biz.IUserBiz;
import com.eyetech.mvp.biz.OnLoginListener;
import com.eyetech.mvp.biz.UserBiz;
import com.eyetech.mvp.view.LoginView;

/**
 * Created by eyetech on 16/5/27.
 * mail:354734713@qq.com
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private IUserBiz mUserBiz;
    private Handler mHandler;

    public LoginPresenter() {
        mUserBiz = new UserBiz();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 登录操作
     */
    public void login() {
        mView.showLoading();
        mUserBiz.login(mView.getUserName(), mView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoginSuccess(user);
                        mView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoginFailed();
                        mView.hideLoading();
                    }
                });

            }
        });


    }

    /**
     * 删除用户信息和密码
     */
    public void cancel() {
        mView.clearUserName();
        mView.clearPassword();
    }

}
