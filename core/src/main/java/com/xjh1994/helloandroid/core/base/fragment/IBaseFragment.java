package com.xjh1994.helloandroid.core.base.fragment;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by xjh1994 on 2016/8/24.
 */

public interface IBaseFragment {

    int getLayoutResId();
    void initArguments(Bundle data);
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
