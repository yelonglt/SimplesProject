package com.eyetech.realm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private static final String TAG=MainActivity.class.getSimpleName();

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm=Realm.getInstance(this);


        findViewById(R.id.realm_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDate();
            }
        });

        findViewById(R.id.realm_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findData();
            }
        });

    }

    private void saveDate(){
        mRealm.beginTransaction();
        Country country=mRealm.createObject(Country.class);

        country.setName("beijing");
        country.setPopulation(12345);
        country.setCode("NO");

        mRealm.commitTransaction();
    }

    private void findData(){
        RealmResults<Country> results=mRealm.where(Country.class).findAll();
        for (Country country:results){
            Log.d(TAG,country.getName());
        }
    }
}
