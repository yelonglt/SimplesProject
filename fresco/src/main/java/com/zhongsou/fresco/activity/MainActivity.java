package com.zhongsou.fresco.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.cache.disk.DiskStorageCache;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.umeng.analytics.MobclickAgent;
import com.zhongsou.fresco.MainApplication;
import com.zhongsou.fresco.R;
import com.zhongsou.fresco.fragment.PictureFragment;
import com.zhongsou.fresco.model.LoadCompleteMessage;
import com.zhongsou.fresco.utils.ProgressDialogUtil;
import com.zhongsou.fresco.utils.ShowToast;
import com.zhongsou.fresco.utils.ToastMsg;
import com.zhongsou.fresco.view.SlidingTabLayout;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.spot.SpotManager;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    private Toolbar mToolbar;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressDialogUtil.showProgressDialog(this, "一大波妹子来袭......");

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorMain));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // use own style rules for tab layout
        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.tab_indicator_color));
        mSlidingTabLayout.setDistributeEvenly(true);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(7);
        mViewPager.setAdapter(new MainTabs(getSupportFragmentManager()));

        mSlidingTabLayout.setViewPager(mViewPager);

        // Tab events
        if (mSlidingTabLayout != null) {
            mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        // 实例化广告条
        AdView adView = new AdView(this, AdSize.FIT_SCREEN);
        LinearLayout layout = (LinearLayout) findViewById(R.id.ad_layout);
        layout.addView(adView);

        // Click events for Navigation Drawer
        ImageView mImageView = (ImageView) findViewById(R.id.iv_girl);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast.Short(ToastMsg.CLEAR_CACHE_SUCCESS);
                ImagePipelineFactory imagePipelineFactory = ImagePipelineFactory.getInstance();
                DiskStorageCache diskStorageCache = imagePipelineFactory.getMainDiskStorageCache();
                diskStorageCache.clearAll();
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

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

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        SpotManager.getInstance(this).onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        SpotManager.getInstance(this).onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ShowToast.Short(ToastMsg.PRESS_AGAIN_EXIT);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);

                /*ActivityManager manager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                manager.restartPackage(getPackageName());*/
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onEventMainThread(LoadCompleteMessage message) {
        ProgressDialogUtil.hideProgressDialog();
    }

    public class MainTabs extends FragmentPagerAdapter {

        public MainTabs(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Fragment getItem(int position) {
            return PictureFragment.newInstance(position + 1);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MainApplication.getContext().getResources().getStringArray(R.array.pic_menu)[position];
        }
    }
}
