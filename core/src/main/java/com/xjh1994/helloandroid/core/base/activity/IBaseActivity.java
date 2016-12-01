package com.xjh1994.helloandroid.core.base.activity;

import android.content.Intent;

/**
 * Created by xjh1994 on 2016/8/24.
 */

public interface IBaseActivity {

    int getLayoutResId();
    void initIntent(Intent intent);
    void initView();
    void initData();

    void startAnimActivity(Class<?> cla);
    void startAnimActivity(Intent intent);
    void toast(String msg);
    void toast(int msgId);
    void toastLong(String msg);
    void toastLong(int msgId);
    void log(Object msg);
}
