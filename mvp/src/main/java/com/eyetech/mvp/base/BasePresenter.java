package com.eyetech.mvp.base;

/**
 * Created by eyetech on 16/5/27.
 * mail:354734713@qq.com
 */
public abstract class BasePresenter<T> {
    public T mView;

    public void attach(T mView){
        this.mView = mView;
    }

    public void detach(){
        mView = null;
    }
}
