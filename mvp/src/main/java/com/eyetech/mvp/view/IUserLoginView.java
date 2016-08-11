package com.eyetech.mvp.view;

import com.eyetech.mvp.bean.User;

/**
 * Created by eyetech on 15/12/14.
 * presenter与view交互是通过接口，我们自定义一个接口类
 */
public interface IUserLoginView {

    String getUserName();

    String getPassword();

    void showLoading();

    void hideLoading();

    void showLoginSuccess(User user);

    void showLoginFailed();

    void clearUserName();

    void clearPassword();


}
