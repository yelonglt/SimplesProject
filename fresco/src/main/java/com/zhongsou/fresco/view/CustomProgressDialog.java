package com.zhongsou.fresco.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongsou.fresco.R;

/**
 * Created by yelong on 2015/11/10.
 */
public class CustomProgressDialog extends Dialog {
    private static CustomProgressDialog mDialog;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static CustomProgressDialog createProgressDialog(Context context, String msg) {
        mDialog = new CustomProgressDialog(context, R.style.ProgressDialogStyle);
        mDialog.setContentView(R.layout.custom_progress_dialog);
        mDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        ImageView mImageView = (ImageView) mDialog.findViewById(R.id.iv_progress);
        final AnimationDrawable mAnimation = (AnimationDrawable) mImageView.getBackground();
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
        TextView mTextView = (TextView) mDialog.findViewById(R.id.tv_progress_dialog_loading);
        if (!TextUtils.isEmpty(msg)) {
            mTextView.setText(msg);
        } else {
            mTextView.setText("~~~");
        }
        return mDialog;
    }

    public static CustomProgressDialog createHelpDialog(Context context) {
        mDialog = new CustomProgressDialog(context, R.style.HelpDialogStyle);
        mDialog.setContentView(R.layout.custom_help_dialog);
        mDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        ImageView mImageView = (ImageView) mDialog.findViewById(R.id.help);
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDialog.dismiss();
                return false;
            }
        });
        return mDialog;
    }
}
