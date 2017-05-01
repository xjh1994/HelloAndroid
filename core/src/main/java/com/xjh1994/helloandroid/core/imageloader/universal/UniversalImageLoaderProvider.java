package com.xjh1994.helloandroid.core.imageloader.universal;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xjh1994.helloandroid.core.imageloader.BaseImageLoaderProvider;
import com.xjh1994.helloandroid.core.imageloader.CommonImageLoader;
import com.xjh1994.helloandroid.core.imageloader.XImageLoader;
import com.xjh1994.helloandroid.core.util.NetUtils;

/**
 * Created by xjh1994 on 2016/5/30.
 * UniversalImageLoader加载
 */
public class UniversalImageLoaderProvider extends BaseImageLoaderProvider {

    @Override
    public void loadImage(Context context, CommonImageLoader img) {
        if (img.getStrategy() == XImageLoader.LOAD_STRATEGY_ONLY_WIFI && !NetUtils.isWifi(context)) {
            //只在WiFi下加载
            img.getImgView().setImageResource(img.getPlaceHolder());
            return;
        }

        DisplayImageOptions options;
        if (img.isCircle()) {
            options = UniversalImageLoaderTool.getCircleDisplayImageOptions(img.getPlaceHolder());
        } else if (img.isRound()) {
            options = UniversalImageLoaderTool.getRoundDisplayImageOptions(img.getPlaceHolder(), img.getRoundRadius());
        } else {
            options = UniversalImageLoaderTool.getDisplayImageOptions(img.getPlaceHolder());
        }

        ImageLoader.getInstance().displayImage(img.getUrl(), img.getImgView(), options,
                UniversalImageLoaderTool.getAnimateFirstDisplayListener());
    }
}
