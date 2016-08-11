package com.zhongsou.fresco.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.zhongsou.fresco.R;

import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

/**
 * Created by yelong on 2015/11/10.
 */
public class SplashActivity extends BaseActivity {
    private Context context;
    private SplashView splashView;
    private View splash;
    private RelativeLayout splashLayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    findViewById(R.id.img1).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    findViewById(R.id.img2).setVisibility(View.VISIBLE);
                    break;
                case 3:
                    findViewById(R.id.img3).setVisibility(View.VISIBLE);
                    break;
                case 4:

                    findViewById(R.id.img4).setVisibility(View.VISIBLE);
                    break;
                case 5:
                    findViewById(R.id.img5).setVisibility(View.VISIBLE);
                    break;

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

        // 第二个参数传入目标activity，或者传入null，改为setIntent传入跳转的intent
        splashView = new SplashView(context, null);
        // 设置是否显示倒数
        splashView.setShowReciprocal(true);
        // 隐藏关闭按钮
        splashView.hideCloseBtn(true);

        Intent intent = new Intent(context, MainActivity.class);
        splashView.setIntent(intent);
        splashView.setIsJumpTargetWhenFail(true);

        splash = splashView.getSplashView();

        splashLayout = ((RelativeLayout) findViewById(R.id.splashview));
        splashLayout.setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
        params.addRule(RelativeLayout.ABOVE, R.id.cutline);
        splashLayout.addView(splash, params);

        SpotManager.getInstance(context).showSplashSpotAds(context, splashView, new SpotDialogListener() {
            @Override
            public void onShowSuccess() {
                splashLayout.setVisibility(View.VISIBLE);
                splashLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.pic_enter_anim_alpha));
            }

            @Override
            public void onShowFailed() {

            }

            @Override
            public void onSpotClosed() {

            }

            @Override
            public void onSpotClick() {

            }
        });

        handler.sendEmptyMessageDelayed(1, 500);
        handler.sendEmptyMessageDelayed(2, 1000);
        handler.sendEmptyMessageDelayed(3, 1500);
        handler.sendEmptyMessageDelayed(4, 2000);
        handler.sendEmptyMessageDelayed(5, 2500);
    }

    // 请务必加上词句，否则进入网页广告后无法进去原sdk
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 10045) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }
}
