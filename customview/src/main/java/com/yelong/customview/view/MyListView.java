package com.yelong.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yelong.customview.R;


public class MyListView extends ListView implements OnTouchListener,
		OnGestureListener {

	private GestureDetector detector;
	private onDeleteListener listener;

	private View deleteView;
	private ViewGroup viewGroup;

	private int selectItem;
	private boolean isDeleteViewShow;

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		detector = new GestureDetector(getContext(), this);
		setOnTouchListener(this);
	}

	/**
	 * 设置删除监听
	 * 
	 * @param listener
	 */
	public void setOnDeleteListener(onDeleteListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (isDeleteViewShow) {
			viewGroup.removeView(deleteView);
			deleteView = null;
			isDeleteViewShow = false;
			return false;
		} else {
			return detector.onTouchEvent(event);
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		if (!isDeleteViewShow) {
			selectItem = pointToPosition((int) e.getX(), (int) e.getY());
		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (!isDeleteViewShow && Math.abs(velocityX) > Math.abs(velocityY)) {
			deleteView = LayoutInflater.from(getContext()).inflate(
					R.layout.view_delete, null);
			deleteView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					viewGroup.removeView(deleteView);
					deleteView = null;
					isDeleteViewShow = false;
					listener.onDelete(selectItem);
				}
			});

			viewGroup = (ViewGroup) getChildAt(selectItem
					- getFirstVisiblePosition());
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);
			viewGroup.addView(deleteView, params);
			isDeleteViewShow = true;
		}

		return false;
	}

	public interface onDeleteListener {
		void onDelete(int position);
	}
}
