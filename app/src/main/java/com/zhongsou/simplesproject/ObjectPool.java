package com.zhongsou.simplesproject;

import android.util.SparseArray;

/**
 * 对象池模式，用于大量创建对象。RecyclerView中View的重用使用的就是这个模式。
 * Created by yelong on 2018/4/24.
 * mail:354734713@qq.com
 */
public abstract class ObjectPool<T> {

    private SparseArray<T> freePool;
    private SparseArray<T> usedPool;
    private final Object lockObj = new Object();
    private int maxCapacity;

    public ObjectPool(int initCapacity, int maxCapacity) {
        init(initCapacity);
        this.maxCapacity = maxCapacity;
    }

    private void init(final int initCapacity) {
        usedPool = new SparseArray<>();
        freePool = new SparseArray<>();
        for (int i = 0; i < initCapacity; i++) {
            freePool.put(i, create());
        }
    }

    public T acquire() {
        T t = null;
        synchronized (lockObj) {
            int freeSize = freePool.size();
            for (int i = 0; i < freeSize; i++) {
                int key = freePool.keyAt(i);
                t = freePool.get(key);
                if (t != null) {
                    this.usedPool.put(key, t);
                    this.freePool.remove(key);
                    return t;
                }
            }

            if (usedPool.size() + freeSize < maxCapacity) {
                t = create();
                usedPool.put(usedPool.size() + freeSize, t);
            }
        }
        return t;
    }

    public void release(T t) {
        if (t == null) {
            return;
        }
        int key = usedPool.indexOfValue(t);
        restore(t);
        this.freePool.put(key, t);
        this.usedPool.remove(key);
    }

    protected abstract T create();

    protected void restore(T t) {

    }
}
