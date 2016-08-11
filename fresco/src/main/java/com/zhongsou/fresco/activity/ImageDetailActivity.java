package com.zhongsou.fresco.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.cache.disk.DiskStorageCache;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.zhongsou.fresco.R;
import com.zhongsou.fresco.model.PictureShow;
import com.zhongsou.fresco.net.Request4PictureMore;
import com.zhongsou.fresco.net.RequestManager;
import com.zhongsou.fresco.utils.ConfigConstants;
import com.zhongsou.fresco.utils.Drawables;
import com.zhongsou.fresco.utils.FileUtil;
import com.zhongsou.fresco.utils.SharedPreUtils;
import com.zhongsou.fresco.utils.ShowToast;
import com.zhongsou.fresco.utils.ToastMsg;
import com.zhongsou.fresco.view.CustomProgressDialog;
import com.zhongsou.fresco.view.GooeyMenu;
import com.zhongsou.fresco.view.JellyViewPager;

import net.youmi.android.spot.SpotManager;

import java.io.File;
import java.util.ArrayList;

/**
 * 图片详情页面
 * Created by yelong on 2015/11/12.
 */
public class ImageDetailActivity extends BaseActivity implements GooeyMenu.GooeyMenuInterface {

    private int adPosition = -1;
    private String id = "1";
    private int color;
    private MyPagerAdapter mAdapter;
    private JellyViewPager mViewPager;
    private GooeyMenu mGooeyMenu;
    private ArrayList<PictureShow> pics = new ArrayList<>();
    private int currPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_image_detail);

        id = getIntent().getStringExtra("id");
        color = getIntent().getIntExtra("color", 0xff218868);
        String fistUrl = getIntent().getStringExtra("url");
        PictureShow pic = new PictureShow();
        pic.setSrc(fistUrl.replace(ConfigConstants.mainUrl, ""));
        pics.add(pic);

        initView();
        initData();
    }

    private void initView() {
        // 加载插播资源
        SpotManager.getInstance(this).loadSpotAds();
        // 插屏出现动画效果，0:ANIM_NONE为无动画，1:ANIM_SIMPLE为简单动画效果，2:ANIM_ADVANCE为高级动画效果
        SpotManager.getInstance(this).setAnimationType(
                SpotManager.ANIM_ADVANCE);
        // 设置插屏动画的横竖屏展示方式，如果设置了横屏，则在有广告资源的情况下会是优先使用横屏图。
        SpotManager.getInstance(this).setSpotOrientation(
                SpotManager.ORIENTATION_LANDSCAPE);
        //SpotManager.getInstance(this).showSpotAds(this);

        findViewById(R.id.image_detail_layout).setBackgroundColor(color);
        mGooeyMenu = (GooeyMenu) findViewById(R.id.gooey_menu);
        mGooeyMenu.setOnMenuListener(this);
        mViewPager = (JellyViewPager) findViewById(R.id.view_pager);
        mAdapter = new MyPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currPosition = position;
                if (currPosition == adPosition) {
                    SpotManager.getInstance(ImageDetailActivity.this).showSpotAds(ImageDetailActivity.this);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case 1: //正在滑动
                        break;
                    case 2: //滑动结束
                        break;
                }
            }
        });

        if (SharedPreUtils.getBoolean(this, "is_first", true)) {
            CustomProgressDialog.createHelpDialog(this);
            SharedPreUtils.setBoolean(this, "is_first", false);
        } else {
            SpotManager.getInstance(this).showSpotAds(this);
        }
    }

    private void initData() {
        Request request = new Request4PictureMore(PictureShow.URL + id, new Response.Listener<ArrayList<PictureShow>>() {
            @Override
            public void onResponse(ArrayList<PictureShow> response) {
                if (response != null && response.size() > 0) {
                    adPosition = response.size() - 1;
                    response.remove(0);
                    pics.addAll(response);
                    mViewPager.setAdapter(mAdapter);
                } else {
                    ShowToast.Long(ToastMsg.NO_MORE_PICTURE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShowToast.Short(ToastMsg.LOAD_FAILED);
            }
        });

        RequestManager.addRequest(request, this);
    }

    /**
     * 获取选择的文件
     *
     * @param url
     * @return
     */
    public File getSelectFile(String url) {
        CacheKey cacheKey = new SimpleCacheKey(url);
        ImagePipelineFactory imagePipelineFactory = ImagePipelineFactory.getInstance();
        DiskStorageCache diskStorageCache = imagePipelineFactory.getMainDiskStorageCache();
        FileBinaryResource binaryResource = (FileBinaryResource) diskStorageCache.getResource(cacheKey);
        return binaryResource == null ? null : binaryResource.getFile();
    }

    /**
     * 显示对应的图片
     *
     * @param url
     * @param draweeView
     */
    private void showImgs(String url, final SimpleDraweeView draweeView) {
        final Uri uri = Uri.parse(url);
        ControllerListener listener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }

                draweeView.setAspectRatio(((float) imageInfo.getWidth() / (float) imageInfo.getHeight()));
            }

        };
        GenericDraweeHierarchy gdh = new GenericDraweeHierarchyBuilder(getApplicationContext().getResources())
                .setPlaceholderImage(Drawables.sPlaceholderDrawable)
                .setFailureImage(Drawables.sPlaceholderDrawable)
                        //.setRoundingParams(RoundingParams.asCircle())
                .setProgressBarImage(new ProgressBarDrawable())
                .build();


        final ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(uri)
                        //	.setResizeOptions(new ResizeOptions(300, 600))
                        //	.setProgressiveRenderingEnabled(true)
                        //	.setLocalThumbnailPreviewsEnabled(true)
                        .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setTapToRetryEnabled(true)
                .setOldController(draweeView.getController())
                .setControllerListener(listener)
                .setAutoPlayAnimations(true)
                .build();
        draweeView.setHierarchy(gdh);
        draweeView.setController(controller);
    }

    @Override
    public void onBackPressed() {
        // 如果有需要，可以点击后退关闭插播广告。
        if (!SpotManager.getInstance(this).disMiss()) {
            // 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
            super.onBackPressed();
        }
    }

    @Override
    public void menuOpen() {

    }

    @Override
    public void menuClose() {

    }

    @Override
    public void menuItemClicked(int menuNumber) {
        switch (menuNumber) {
            case 1:
                finish();
                break;
            case 2:
                FileUtil.savePicture(getSelectFile(pics.get(currPosition).getSrc()));
                break;
            case 3:
                FileUtil.sharePicture(this, getSelectFile(pics.get(currPosition).getSrc()), "分享来自美女写真");
                break;
        }
    }

    public class MyPagerAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        public MyPagerAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return pics.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.item_img_detail, container, false);
            SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.img_detail);
            showImgs(pics.get(position).getSrc(), draweeView);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
