package com.dmall.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dmall.retrofit.dto.UserListDTO;
import com.dmall.retrofit.net.NetManager;
import com.dmall.retrofit.vo.User;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetManager.getApiService().getUsers("yelong").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<User>>() {
            @Override
            public void call(List<User> users) {
                for (User user : users) {
                    System.out.println("name==" + user.getUsername());
                    System.out.println("age==" + user.getAge());
                    System.out.println("address==" + user.getAddress());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("error==" + throwable.toString());
            }
        });


        NetManager.getApiService().getUser("yelong").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<User>() {
            @Override
            public void call(User user) {
                System.out.println(user.getAddress());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("error==" + throwable.toString());
            }
        });
    }

    public void test() {
        NetManager.getApiService().getUsers().subscribeOn(Schedulers.io())
                .map(new Func1<UserListDTO, List<User>>() {
                    @Override
                    public List<User> call(UserListDTO userListDTO) {
                        return userListDTO.transform();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) {

                    }
                });
    }
}
