package com.zhongsou.fresco.utils;

import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Sets;
import com.facebook.common.internal.Supplier;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

/**
 * Creates ImagePipeline configuration
 * Created by yelong on 2015/11/10.
 */
public class ImagePipelineConfigFactory {
    private static ImagePipelineConfig mImagePipelineConfig;

    /**
     * Creates config using android http stack as network backend.
     *
     * @return
     */
    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (mImagePipelineConfig == null) {
            ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context);
            configureCaches(builder, context);
            configureLoggingListeners(builder);
            mImagePipelineConfig = builder.build();
        }
        return mImagePipelineConfig;
    }

    /**
     * Configures disk and memory cache not to exceed common limits
     *
     * @param builder
     * @param context
     */
    private static void configureCaches(ImagePipelineConfig.Builder builder, Context context) {
        final MemoryCacheParams params = new MemoryCacheParams(
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);                    // Max cache entry size

        builder.setBitmapMemoryCacheParamsSupplier(new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return params;
            }
        });
        builder.setMainDiskCacheConfig(DiskCacheConfig.newBuilder()
                .setBaseDirectoryPath(FileUtil.getOwnCacheDirectory(context))
                .setBaseDirectoryName(FileUtil.IMAGE_PIPELINE_CACHE_DIR)
                .setMaxCacheSize(ConfigConstants.MAX_DISK_CACHE_SIZE).build());
        builder.build();
    }

    /**
     * @param builder
     */
    private static void configureLoggingListeners(ImagePipelineConfig.Builder builder) {
        builder.setRequestListeners(Sets.<RequestListener>newHashSet(new RequestLoggingListener()));
    }
}
