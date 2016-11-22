package com.xjh1994.helloandroid.core;

import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;


/**
 * Created by xjh1994 on 16/11/22.
 */

public class CoreApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initLog();
    }

    private void initLog() {
        Logger
                .init(getString(R.string.app_name_core))
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
    }
}
