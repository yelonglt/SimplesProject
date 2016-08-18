package com.yelong.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = (RelativeLayout) findViewById(R.id.contentLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = mView.getMeasuredWidth();
                int height = mView.getMeasuredHeight();
                System.out.println("width=" + width + "--height=" + height);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int width = mView.getMeasuredWidth();
            int height = mView.getMeasuredHeight();
            System.out.println("width=" + width + "--height=" + height);
        }
    }
}
