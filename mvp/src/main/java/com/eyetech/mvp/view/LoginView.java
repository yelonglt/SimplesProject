package com.eyetech.mvp.view;

import com.eyetech.mvp.base.BaseView;
import com.eyetech.mvp.bean.User;

/**
 * Created by eyetech on 16/5/27.
 * mail:354734713@qq.com
 */
public interface LoginView extends BaseView {

    void showLoginSuccess(User user);

    void showLoginFailed();

    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();
}
