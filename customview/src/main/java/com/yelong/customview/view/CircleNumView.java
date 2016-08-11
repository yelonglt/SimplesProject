package com.yelong.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.yelong.customview.R;

/**
 * 自定义圆圈中添加数字
 * Created by yelong on 16/8/11.
 * mail:354734713@qq.com
 */
public class CircleNumView extends View {

    private int circleColor = 0xFFFF4081;
    private int circleWidth = 5;
    private int textColor = 0xFFFF4081;
    private int textSize = 30;
    private String text;

    private Paint mPaint;

    public CircleNumView(Context context) {
        this(context, null);
    }

    public CircleNumView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleNumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化默认值
        DisplayMetrics dm = getResources().getDisplayMetrics();
        circleWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, circleWidth, dm);
        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, textSize, dm);

        //读取设置的属性值
        TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CircleNumView, defStyleAttr, 0);

        for (int i = 0; i < array.getIndexCount(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CircleNumView_cnvCircleColor:
                    circleColor = array.getColor(attr, circleColor);
                    break;
                case R.styleable.CircleNumView_cnvCircleWidth:
                    circleWidth = array.getDimensionPixelSize(attr, circleWidth);
                    break;
                case R.styleable.CircleNumView_cnvTextColor:
                    textColor = array.getColor(attr, textColor);
                    break;
                case R.styleable.CircleNumView_cnvTextSize:
                    textSize = array.getDimensionPixelSize(attr, textSize);
                    break;
                case R.styleable.CircleNumView_cnvText:
                    text = array.getString(attr);
                    break;
            }
        }
        array.recycle();

        mPaint = new Paint();
    }

    /**
     * 设置圆圈的文本
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthMode == MeasureSpec.EXACTLY ? widthSize : 100;
        int height = heightMode == MeasureSpec.EXACTLY ? heightSize : 100;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int radius = (centerX < centerY ? centerX : centerY) - circleWidth / 2;
        mPaint.setStrokeWidth(circleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mPaint.setColor(circleColor);
        canvas.drawCircle(centerX, centerY, radius, mPaint); // 画出圆环

        Rect targetRect = new Rect(0, 0, getWidth(), getHeight());
        mPaint.setStrokeWidth(0);
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, targetRect.centerX(), baseline, mPaint);
    }
}
