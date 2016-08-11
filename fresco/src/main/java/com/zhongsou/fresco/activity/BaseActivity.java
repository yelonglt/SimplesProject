package com.zhongsou.fresco.activity;

import android.app.Activity;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by yelong on 2015/11/10.
 */
public class BaseActivity extends Activity{

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
