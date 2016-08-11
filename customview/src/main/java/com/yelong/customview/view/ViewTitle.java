package com.yelong.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.yelong.customview.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * 自绘控件
 *
 * @author 800hr：yelong
 *         <p/>
 *         2015-6-3
 */
public class ViewTitle extends View {

    private String text;
    private int textColor;
    private float textSize;

    private Rect mBound;
    private Paint mPaint;

    public ViewTitle(Context context) {
        super(context);
    }

    public ViewTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载自定义的属性
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.Header);

        //遍历所有的属性
        for (int i = 0; i < array.getIndexCount(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.Header_text:
                    text = array.getString(R.styleable.Header_text);
                    break;
                case R.styleable.Header_textColor:
                    textColor = array.getColor(R.styleable.Header_textColor, Color.BLACK);
                    break;
                case R.styleable.Header_textSize:
                    textSize = array.getDimension(R.styleable.Header_textSize, 20f);
                    break;
            }
        }
        // 回收资源，这一句必须调用
        array.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        // mPaint.setColor(textColor);
        mBound = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), mBound);

        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                text = randomText();
                postInvalidate();
            }
        });

    }

    /**
     * MeasureSpec的specMode,一共三种类型：
     * <p/>
     * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
     * <p/>
     * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
     * <p/>
     * UNSPECIFIED：表示子布局想要多大就多大，很少使用
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(text, 0, text.length(), mBound);
            width = getPaddingLeft() + mBound.width() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(text, 0, text.length(), mBound);
            height = getPaddingTop() + mBound.height() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(textColor);
        canvas.drawText(text, getWidth() / 2 - mBound.width() / 2, getHeight()
                / 2 + mBound.height() / 2, mPaint);
    }

    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }

        return sb.toString();
    }

}
