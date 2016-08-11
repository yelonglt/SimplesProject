package com.eyetech.mvp.presenter;

import android.os.Handler;

import com.eyetech.mvp.bean.User;
import com.eyetech.mvp.biz.IUserBiz;
import com.eyetech.mvp.biz.OnLoginListener;
import com.eyetech.mvp.biz.UserBiz;
import com.eyetech.mvp.view.IUserLoginView;

/**
 * Created by eyetech on 15/12/14.
 * 负责完成用户登录View和Model之间的交互
 */
public class UserLoginPresenter {

    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private Handler mHandler = new Handler();


    public UserLoginPresenter(IUserLoginView userLoginView) {
        this.userLoginView = userLoginView;
        this.userBiz = new UserBiz();
    }

    /**
     * 登录操作
     */
    public void login() {
        userLoginView.showLoading();
        userBiz.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.showLoginSuccess(user);
                        userLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.showLoginFailed();
                        userLoginView.hideLoading();
                    }
                });

            }
        });


    }

    /**
     * 删除用户信息和密码
     */
    public void cancel() {
        userLoginView.clearUserName();
        userLoginView.clearPassword();
    }

}
