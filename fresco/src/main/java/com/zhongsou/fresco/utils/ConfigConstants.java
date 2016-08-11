package com.zhongsou.fresco.utils;

import com.facebook.common.util.ByteConstants;

/**
 * Created by yelong on 2015/11/10.
 */
public class ConfigConstants {
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    public static final int MAX_DISK_CACHE_SIZE = 80 * ByteConstants.MB;
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 12;

    public static  final String mainUrl="http://tnfs.tngou.net/img";
}
