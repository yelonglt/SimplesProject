package com.dmall.retrofit.dto;

import com.dmall.retrofit.vo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yelong on 2017/2/15.
 * mail:354734713@qq.com
 */
public class UserListDTO implements Mapper<List<User>>{

    public int version;
    public List<UserDTO> userList;

    @Override
    public List<User> transform() {
        List<User> list = new ArrayList<>();
        for (UserDTO userDTO:userList){
            list.add(userDTO.transform());
        }
        return list;
    }
}
