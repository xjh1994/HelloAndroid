package com.xjh1994.helloandroid.core.util.analysis;

import android.content.Context;

/**
 * Created by xjh1994 on 2016/9/21.
 * 应用统计通用接口
 */

public interface IAnalysisProvider {

    void init(Context context, Object... params);
    void setDefaultReportPolicy(Context context, Object policy);

    void onResume(Context context);
    void onPause(Context context);
    void onEvent(Context context, String eventId);
    void onEvent(Context context, String eventId, String label);
    void onError(Context context, String eventId);
    void onFragmentResume(Context context, String pageName);
    void onFragmentPause(Context context, String pageName);

    void bindUserId(Context context, String userId);
    void setDebugMode(boolean isDebug);

    void update(Context context);
}
