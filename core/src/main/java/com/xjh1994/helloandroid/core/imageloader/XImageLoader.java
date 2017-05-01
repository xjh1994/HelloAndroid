package com.xjh1994.helloandroid.core.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.xjh1994.helloandroid.core.imageloader.universal.UniversalImageLoaderProvider;


/**
 * Created by xjh1994 on 2016/5/30.
 * XImageLoader 统一图片加载入口 TODO GlideProvider
 */
public class XImageLoader {
    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    public static final int ROUND_CORNER_RADIUS = 20;

    private volatile static XImageLoader mInstance;
    private BaseImageLoaderProvider mProvider;

    private XImageLoader() {
        mProvider = new UniversalImageLoaderProvider();
    }

    //single instance
    public static XImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (XImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new XImageLoader();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public synchronized void init(BaseImageLoaderProvider imageLoaderProvider) {
        this.mProvider = imageLoaderProvider;
    }

    public void loadImage(Context context, CommonImageLoader img) {
        mProvider.loadImage(context, img);
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .build();
        mProvider.loadImage(context, imageLoader);
    }
}
