package com.xjh1994.helloandroid.core.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.tianxiabuyi.txutils.TxImageLoader;
import com.tianxiabuyi.txutils.imageloader.BaseImageLoaderProvider;
import com.tianxiabuyi.txutils.imageloader.CommonImageLoader;
import com.tianxiabuyi.txutils.util.NetUtils;

/**
 * Created by xjh1994 on 2016/9/1.
 */

public class GlideImageLoaderProvider extends BaseImageLoaderProvider {

    @Override
    public void loadImage(Context context, CommonImageLoader img) {
        if (img.getStrategy() == TxImageLoader.LOAD_STRATEGY_ONLY_WIFI && !NetUtils.isWifi(context)) {
            //只在WiFi下加载
            img.getImgView().setImageResource(img.getPlaceHolder());
            return;
        }

        String url = img.getUrl();

        if (img.isBitmap()) {   //bitmap方式加载
            try {
                int resId = Integer.parseInt(url);
                Glide.with(context).load(resId).asBitmap().into(img.getImgView());
            } catch (NumberFormatException e) {
                Glide.with(context).load(url).asBitmap().into(img.getImgView());
            }
            return;
        }

        DrawableRequestBuilder requestBuilder;

        try {   //加载本地资源文件
            int resId = Integer.parseInt(url);
            requestBuilder = Glide.with(context).load(resId).placeholder(img.getPlaceHolder());
        } catch (NumberFormatException e) {
            requestBuilder = Glide.with(context).load(url).placeholder(img.getPlaceHolder());
        }

        if (img.isCircle()) {
            requestBuilder.bitmapTransform(new CropCircleTransformation(context));
        }
        if (img.isRound()) {
            requestBuilder.bitmapTransform(new RoundedCornersTransformation(context, img.getRoundRadius(), 0));
        }
        if (img.getSize() != null) {
            requestBuilder.override(img.getSize().getWidth(), img.getSize().getHeight());
        }
        requestBuilder.into(img.getImgView());
    }
}
