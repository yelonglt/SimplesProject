package com.zhongsou.fresco.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhongsou.fresco.R;
import com.zhongsou.fresco.adapter.PictureAdapter;
import com.zhongsou.fresco.net.LoadFinishCallBack;
import com.zhongsou.fresco.utils.Constant;
import com.zhongsou.fresco.view.AutoLoadRecyclerView;

/**
 * Created by yelong on 2015/11/10.
 */
public class PictureFragment extends Fragment {

    private AutoLoadRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout load_fail;

    private PictureAdapter mAdapter;
    private LoadFinishCallBack mLoadFinishCallBack;

    private int fragmentType = 1;

    public PictureFragment() {
    }

    public static PictureFragment newInstance(int fragmentType) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.FRAGMENT_TYPE, fragmentType);
        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            fragmentType = bundle.getInt(Constant.FRAGMENT_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        mRecyclerView = (AutoLoadRecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        load_fail = (LinearLayout) view.findViewById(R.id.load_fail);

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLoadFinishCallBack = mRecyclerView;
        mRecyclerView.setLoadMoreListener(new AutoLoadRecyclerView.onLoadMoreListener() {

            @Override
            public void loadMore() {
                mAdapter.loadNextPage();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.loadFirst();
            }
        });

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new PictureAdapter(getActivity(), mSwipeRefreshLayout, load_fail, mLoadFinishCallBack, fragmentType);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadFirst();
    }
}
