package com.zhongsou.fresco.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.zhongsou.fresco.net.LoadFinishCallBack;

/**
 * Created by yelong on 2015/11/10.
 */
public class AutoLoadRecyclerView extends RecyclerView implements LoadFinishCallBack {
    private onLoadMoreListener loadMoreListener;
    private boolean isLoadingMore;

    public AutoLoadRecyclerView(Context context) {
        super(context);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        isLoadingMore = false;
        setOnScrollListener(new AutoLoadScrollListener());
    }

    @Override
    public void loadFinish(Object obj) {
        isLoadingMore = false;
    }

    public interface onLoadMoreListener {
        void loadMore();
    }

    public void setLoadMoreListener(onLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    private class AutoLoadScrollListener extends OnScrollListener {
        public AutoLoadScrollListener() {
            super();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int totalItemCount = AutoLoadRecyclerView.this.getAdapter().getItemCount();
            //由于GridLayoutManager是LinearLayoutManager子类，所以也适用
            if (getLayoutManager() instanceof LinearLayoutManager) {
                int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                //有回调接口，并且不是加载状态，并且剩下2个item，并且向下滑动，则自动加载
                if (loadMoreListener != null && !isLoadingMore && lastVisibleItem >= totalItemCount -
                        2 && dy > 0) {
                    loadMoreListener.loadMore();
                    isLoadingMore = true;
                }
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                int[] lastVisibleItem = ((StaggeredGridLayoutManager) getLayoutManager()).findLastVisibleItemPositions(new int[2]);
                if (loadMoreListener != null && !isLoadingMore && lastVisibleItem[0] >= totalItemCount -
                        4 && dy > 0) {
                    loadMoreListener.loadMore();
                    isLoadingMore = true;
                }
            }
        }
    }
}
