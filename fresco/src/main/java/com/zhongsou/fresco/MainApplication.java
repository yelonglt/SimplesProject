package com.zhongsou.fresco;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zhongsou.fresco.utils.ImagePipelineConfigFactory;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotManager;

/**
 * Created by yelong on 2015/11/10.
 */
public class MainApplication extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //有米sdk
        AdManager.getInstance(mContext).init("f8f383dd83e626c3", "ed6bf7e5be27015e", false);
        SpotManager.getInstance(mContext).loadSpotAds();

        //facebook的图片处理框架
        Fresco.initialize(mContext, ImagePipelineConfigFactory.getImagePipelineConfig(mContext));
    }
}
