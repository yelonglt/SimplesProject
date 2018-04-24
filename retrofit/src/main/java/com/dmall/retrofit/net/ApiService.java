package com.dmall.retrofit.net;

import com.dmall.retrofit.dto.UserListDTO;
import com.dmall.retrofit.vo.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by eyetech on 16/5/20.
 * mail:354734713@qq.com
 */
public interface ApiService {

    @GET("users")
    Observable<UserListDTO> getUsers();

    @GET("users")
    Observable<List<User>> getUsers(@Query("username") String username);

    @GET("user")
    Observable<User> getUser(@Query("username") String username);

}
