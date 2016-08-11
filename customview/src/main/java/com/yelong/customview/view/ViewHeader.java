package com.yelong.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yelong.customview.R;


/**
 * 组合控件，内部包含一个TextView和一个ImageView
 * 
 * @author 800hr：yelong
 * 
 *         2015-6-3
 */
public class ViewHeader extends RelativeLayout {

	private TextView header_tv;
	private ImageView header_iv;

	private String text;
	private int textColor;
	private float textSize;

	public ViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 加载视图的布局
		LayoutInflater.from(context).inflate(R.layout.view_header, this, true);
		// 加载自定义的属性
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.Header);
		text = array.getString(R.styleable.Header_text);
		textColor = array.getColor(R.styleable.Header_textColor, Color.BLACK);
		textSize = array.getDimension(R.styleable.Header_textSize, 20f);
		// 回收资源，这一句必须调用
		array.recycle();
	}

	/**
	 * 此方法会在所有的控件都从xml文件中加载完成后调用
	 */
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		// 获取子控件
		header_tv = (TextView) findViewById(R.id.header_tv);
		header_iv = (ImageView) findViewById(R.id.header_iv);

		if (!TextUtils.isEmpty(text))
			setText(text);

		header_tv.setTextColor(textColor);
		header_tv.setTextSize(textSize);
	}

	public void setText(String text) {
		header_tv.setText(text);
	}

	public void setTextColor(int color) {
		header_tv.setTextColor(color);
	}

	public void setTextSize(float size) {
		header_tv.setTextSize(size);
	}

	/**
	 * 设置右侧图片的点击事件
	 * 
	 * @param listener
	 */
	public void setOnHeaderClickListener(OnClickListener listener) {
		header_iv.setOnClickListener(listener);
	}

}
