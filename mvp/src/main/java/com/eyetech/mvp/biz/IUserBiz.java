package com.eyetech.mvp.biz;

/**
 * Created by eyetech on 15/12/14.
 * User登录操作接口
 */
public interface IUserBiz {

    void login(String username,String password,OnLoginListener listener);
}
