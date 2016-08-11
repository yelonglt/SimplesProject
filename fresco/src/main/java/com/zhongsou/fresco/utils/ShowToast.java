package com.zhongsou.fresco.utils;

import android.widget.Toast;

import com.zhongsou.fresco.MainApplication;


public class ShowToast {

    /**
     * 显示短时间Toast
     *
     * @param sequence
     */
    public static void Short(CharSequence sequence) {
        Toast.makeText(MainApplication.getContext(), sequence, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长时间Toast
     *
     * @param sequence
     */
    public static void Long(CharSequence sequence) {
        Toast.makeText(MainApplication.getContext(), sequence, Toast.LENGTH_SHORT).show();
    }

}
