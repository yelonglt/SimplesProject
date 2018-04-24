package com.dmall.retrofit.dto;

import com.dmall.retrofit.vo.User;

/**
 * 用户信息
 * Created by yelong on 2017/2/15.
 * mail:354734713@qq.com
 */
public class UserDTO implements Mapper<User> {
    public int uid;
    public String user_name;
    public int age;
    public String address;
    public String image;

    @Override
    public User transform() {

        User user = new User(uid);
        user.setUsername(user_name == null ? "未知" : user_name);
        user.setAge(age);
        user.setAddress(address);
        user.setImage(image);

        return user;
    }
}
