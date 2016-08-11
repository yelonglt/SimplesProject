package com.zhongsou.fresco.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.zhongsou.fresco.MainApplication;
import com.zhongsou.fresco.R;
import com.zhongsou.fresco.activity.ImageDetailActivity;
import com.zhongsou.fresco.model.LoadCompleteMessage;
import com.zhongsou.fresco.model.Picture;
import com.zhongsou.fresco.net.LoadFinishCallBack;
import com.zhongsou.fresco.net.Request4Picture;
import com.zhongsou.fresco.net.RequestManager;
import com.zhongsou.fresco.utils.Drawables;
import com.zhongsou.fresco.wavecompat.WaveCompat;
import com.zhongsou.fresco.wavecompat.WaveDrawable;
import com.zhongsou.fresco.wavecompat.WaveTouchHelper;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by yelong on 2015/11/12.
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private Context context;
    //请求页数
    private int page;
    private ArrayList<Picture> pictures;
    private int lastPosition = -1;
    //没有数据失败的页面
    private View failView;
    private int type;
    //加载完成回调函数
    private LoadFinishCallBack mLoadFinisCallBack;
    //是否有更多图片
    private boolean isNoMorePic = false;
    //瀑布流布局
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public PictureAdapter(Context context, SwipeRefreshLayout mSwipeRefreshLayout, View failView, LoadFinishCallBack mLoadFinisCallBack, int type) {
        this.context = context;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        this.failView = failView;
        this.mLoadFinisCallBack = mLoadFinisCallBack;
        this.type = type;
        pictures = new ArrayList<>();
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                    .anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void loadFirst() {
        page = 1;
        if (failView.getVisibility() == View.VISIBLE) {
            failView.setVisibility(View.GONE);
        }
        loadData();
    }

    public void loadNextPage() {
        if (isNoMorePic) return;
        page++;
        loadData();
    }


    private void loadData() {
        Request request = new Request4Picture(Picture.getRequestUrl(type, page), new Response.Listener<ArrayList<Picture>>() {
            @Override
            public void onResponse(ArrayList<Picture> response) {
                mLoadFinisCallBack.loadFinish(null);
                if (page == 1 && type == 1) {
                    EventBus.getDefault().post(new LoadCompleteMessage(""));
                }

                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (response == null || response.size() == 0) {
                    Toast.makeText(context, "没有更多美女了，请等待更新!", Toast.LENGTH_SHORT).show();
                    isNoMorePic = true;
                    return;
                }

                if (page == 1) {
                    pictures.clear();
                }

                pictures.addAll(response);
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "加载失败!", Toast.LENGTH_SHORT).show();
                if (page == 1 && type == 1) {
                    EventBus.getDefault().post(new LoadCompleteMessage(""));
                }
                if (page == 1 && pictures.size() == 0) {
                    failView.setVisibility(View.VISIBLE);
                }
                mLoadFinisCallBack.loadFinish(null);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        RequestManager.addRequest(request, this);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.card.clearAnimation();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Picture picture = pictures.get(position);
        final String picUrl = picture.getImg();
        Uri uri = Uri.parse(picUrl);

        ControllerListener listener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                if (imageInfo == null) {
                    return;
                }
                holder.img.setAspectRatio(((float) imageInfo.getWidth() / (float) imageInfo.getHeight()));
            }
        };

        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(MainApplication.getContext().getResources())
                .setPlaceholderImage(Drawables.sPlaceholderDrawable)
                .setFailureImage(Drawables.sPlaceholderDrawable)
                .setProgressBarImage(new ProgressBarDrawable()).build();

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(140, 280)).build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setTapToRetryEnabled(true)
                .setOldController(holder.img.getController())
                .setControllerListener(listener)
                .setAutoPlayAnimations(true).build();

        holder.img.setHierarchy(hierarchy);
        holder.img.setController(controller);

        final Intent intent = new Intent(context, ImageDetailActivity.class);
        final int color = context.getResources().getIntArray(R.array.wave_color)[(int) (Math.random() * 5)];
        intent.putExtra("id", picture.getId() + "");
        intent.putExtra("url", picUrl);
        intent.putExtra("color", color);

        WaveTouchHelper.bindWaveTouchHelper(holder.img, new WaveTouchHelper.OnWaveTouchHelperListener() {
            @Override
            public void onWaveTouchUp(View view, Point locationInView, Point locationInScreen) {
                WaveCompat.startWaveFilter((Activity) context,
                        new WaveDrawable().setColor(color).setTouchPoint(locationInScreen), intent);
            }
        });

        holder.tv_pic_count.setText(picture.getSize() + "");
        holder.tv_vist_count.setText(picture.getCount() + "");
        holder.tv_content.setText(picture.getTitle().trim());
        setAnimation(holder.card, position);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_content;
        private TextView tv_pic_count;
        private TextView tv_vist_count;
        private SimpleDraweeView img;
        private CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_pic_count = (TextView) itemView.findViewById(R.id.tv_pic_count);
            tv_vist_count = (TextView) itemView.findViewById(R.id.tv_vist_count);
            img = (SimpleDraweeView) itemView.findViewById(R.id.img);
            card = (CardView) itemView.findViewById(R.id.card);
        }
    }
}
