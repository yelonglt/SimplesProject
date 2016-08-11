package com.yelong.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * TextView的右上角加上红点
 * Created by yelong on 16/7/7.
 * mail:354734713@qq.com
 */
public class RedTipTextView extends TextView {
    public static final int RED_TIP_INVISIBLE = 0;
    public static final int RED_TIP_VISIBLE = 1;
    public static final int RED_TIP_GONE = 2;
    private int tipVisibility = 1;

    public RedTipTextView(Context context) {
        super(context);
    }

    public RedTipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RedTipTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        setPadding(0, 0, 16, 0);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (tipVisibility == 1) {
            int width = getWidth();
            int paddingRight = getPaddingRight();
            int radius;
            if (paddingRight > 16) {
                radius = 8;
            } else {
                radius = paddingRight / 2;
            }
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawCircle(width - paddingRight + radius, radius, radius, paint);
        }
    }

    public void setDotVisibility(int visibility) {
        tipVisibility = visibility;
        invalidate();
    }
}
