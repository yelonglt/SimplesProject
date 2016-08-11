package com.yelong.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.yelong.customview.R;


public class ViewPictrue extends View {
	private Point point = new Point();
	private Child[] childs = new Child[3];
	private Bitmap[] bitmaps;

	public ViewPictrue(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ViewPictrue(Context context) {
		super(context);
		init();
	}

	public ViewPictrue(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		bitmaps = new Bitmap[] {
				((BitmapDrawable) (getContext().getResources()
						.getDrawable(R.mipmap.bird))).getBitmap(),
				((BitmapDrawable) (getContext().getResources()
						.getDrawable(R.mipmap.lion))).getBitmap(),
				((BitmapDrawable) (getContext().getResources()
						.getDrawable(R.mipmap.dog))).getBitmap() };
		WindowManager manager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		int width = manager.getDefaultDisplay().getWidth();

		for (int i = 0; i < childs.length; i++) {
			childs[i] = new Child(i * width, 0, bitmaps[i]);
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < childs.length; i++) {
			childs[i].draw(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_MOVE:
			int tempX = (int) event.getX();
			int offset = (int) (tempX - point.x);
			point.x = tempX;
			for (int i = 0; i < childs.length; i++) {
				childs[i].moveBy(offset, 0);
			}
			// 要求重绘
			invalidate();
			break;
		case MotionEvent.ACTION_DOWN:
			point.x = (int) event.getX();

			break;
		case MotionEvent.ACTION_UP:
			point.x = 0;
			break;
		}
		return true;
	}

	public class Child {
		private int x;
		private int y;
		private Bitmap bitmap;
		private Paint paint;

		public Child(int x, int y, Bitmap bitmap) {
			super();
			this.x = x;
			this.y = y;
			this.bitmap = bitmap;
			paint = new Paint();
			paint.setAntiAlias(true);
		}

		public void draw(Canvas canvas) {
			canvas.drawBitmap(bitmap, x, y, paint);
		}

		public void moveBy(int offset, int i) {
			this.x += offset;
			this.y += i;
		}
	}
}
