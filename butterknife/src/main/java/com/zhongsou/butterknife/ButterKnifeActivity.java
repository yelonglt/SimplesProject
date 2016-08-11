package com.zhongsou.butterknife;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by yelong on 2015/10/12.
 * 使用注解插件  选中对应的layout名称，然后alt+insert generate ButterKnife Injections
 * 使用注解框架
 */
public class ButterKnifeActivity extends Activity {

    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.seekBar)
    SeekBar seekBar;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.butter_knife);

        ButterKnife.bind(this);


    }


    @OnClick(R.id.imageView)
    public void showImage(ImageView imageView) {
        imageView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.textView)
    public void sayHello() {
        Toast.makeText(this, "ssss", Toast.LENGTH_SHORT).show();
    }

    @OnItemClick(R.id.listView)
    public void itemclick(ListView listView) {

    }


}
