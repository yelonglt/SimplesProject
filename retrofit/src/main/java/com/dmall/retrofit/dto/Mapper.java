package com.dmall.retrofit.dto;

/**
 * 传输层数据DTO转化本地数据VO
 * Created by yelong on 2017/2/15.
 * mail:354734713@qq.com
 */
public interface Mapper<T> {

    T transform();
}
