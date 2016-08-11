package com.yelong.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yelong.customview.R;

/**
 * 自定义环形进度条
 * Created by eyetech on 16/6/8.
 * mail:354734713@qq.com
 */
public class CustomProgressBar extends View {

    //第一圈的颜色
    private int mFirstColor;
    //第二圈的颜色
    private int mSecondColor;
    //圆环的宽度
    private int mCircleWidth;
    //圆环移动的速度
    private int mProgressSpeed;

    private Paint mPaint;
    //当前进度
    private int mProgress;
    //是否开启下一个
    private boolean isNext = false;

    private Rect mRect=new Rect();

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);

        for (int i = 0; i < array.getIndexCount(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = array.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = array.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = array.getDimensionPixelSize(attr, (int) TypedValue
                            .applyDimension(TypedValue.COMPLEX_UNIT_PX, 20,
                                    getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_progressSpeed:
                    mProgressSpeed = array.getInt(attr, 20);
                    break;
            }
        }
        array.recycle();

        mPaint = new Paint();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        isNext = !isNext;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mProgressSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int center = getWidth() / 2; // 获取圆心的x坐标
        int radius = center - mCircleWidth / 2;// 半径
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius); // 用于定义的圆弧的形状和大小的界限
        if (!isNext) {// 第一颜色的圈完整，第二颜色跑
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawCircle(center, center, radius, mPaint); // 画出圆环
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        } else {
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawCircle(center, center, radius, mPaint); // 画出圆环
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        }

        String text = mProgress*100/360+"%";
        mPaint.setStrokeWidth(0);
        mPaint.getTextBounds(text,0,text.length(),mRect);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(50);
        canvas.drawText(text, getWidth() / 2 - mRect.width() / 2, getHeight()
                / 2 + mRect.height() / 2, mPaint);

    }
}
