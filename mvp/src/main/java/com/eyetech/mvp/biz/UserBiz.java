package com.eyetech.mvp.biz;

import com.eyetech.mvp.bean.User;

/**
 * Created by eyetech on 15/12/14.
 * User控制逻辑类
 */
public class UserBiz implements IUserBiz {

    @Override
    public void login(final String username, final String password, final OnLoginListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ("jack".equals(username) && "123".equals(password)) {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    listener.loginSuccess(user);
                } else {
                    listener.loginFailed();
                }
            }
        }).start();
    }
}
