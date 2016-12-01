package com.xjh1994.helloandroid.core.util.analysis;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by xjh1994 on 2016/12/1.
 * 友盟统计
 */

public class UmengAnalysisProvider implements IAnalysisProvider {

    @Override
    public void init(Context context, Object... params) {

        MobclickAgent.openActivityDurationTrack(false);
    }

    @Override
    public void setDefaultReportPolicy(Context context, Object policy) {

    }

    @Override
    public void onResume(Context context) {
        MobclickAgent.onPageStart(context.getClass().getSimpleName());
        MobclickAgent.onResume(context);
    }

    @Override
    public void onPause(Context context) {
        MobclickAgent.onPageEnd(context.getClass().getSimpleName());
        MobclickAgent.onPause(context);
    }

    @Override
    public void onEvent(Context context, String eventId) {

    }

    @Override
    public void onEvent(Context context, String eventId, String label) {

    }

    @Override
    public void onError(Context context, String eventId) {

    }

    @Override
    public void onFragmentResume(Context context, String pageName) {
        MobclickAgent.onPageStart("MainScreen");
    }

    @Override
    public void onFragmentPause(Context context, String pageName) {
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    public void bindUserId(Context context, String userId) {

    }

    @Override
    public void setDebugMode(boolean isDebug) {

    }

    @Override
    public void update(Context context) {

    }
}
