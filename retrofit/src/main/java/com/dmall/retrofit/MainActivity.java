package com.dmall.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dmall.retrofit.net.NetManager;
import com.dmall.retrofit.net.User;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
                for (User user:users) {
                    System.out.println("name==" + user.username);
                    System.out.println("age==" + user.age);
                    System.out.println("address==" + user.address);
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("error=="+throwable.toString());
            }
        });


        NetManager.getApiService().getUser("yelong").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<User>() {
            @Override
            public void call(User user) {
                System.out.println(user.address);
            }
        },new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("error=="+throwable.toString());
            }
        });
    }
}
