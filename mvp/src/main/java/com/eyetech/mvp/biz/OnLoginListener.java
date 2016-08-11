package com.eyetech.mvp.biz;

import com.eyetech.mvp.bean.User;

/**
 * Created by eyetech on 15/12/14.
 * 登录成功或者失败的接口
 */
public interface OnLoginListener {

    void loginSuccess(User user);

    void loginFailed();
}
