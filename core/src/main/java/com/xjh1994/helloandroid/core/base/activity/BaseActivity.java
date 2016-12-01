package com.xjh1994.helloandroid.core.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.orhanobut.logger.Logger;
import com.xjh1994.helloandroid.core.util.ActivityUtils;
import com.xjh1994.helloandroid.core.util.ToastUtils;
import com.xjh1994.helloandroid.core.util.analysis.AnalysisUtils;

import butterknife.ButterKnife;

/**
 * Created by xjh1994 on 2016/8/24.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    protected InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        afterOnCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);

        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
        }
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * 基类ActivityOnCreate之前
     *
     * @param savedInstanceState
     */
    protected void beforeOnCreate(Bundle savedInstanceState) {

    }

    /**
     * 基类Activity OnCreate之后，setContentView之前
     *
     * @param savedInstanceState
     */
    protected void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityUtils.getInstance().finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalysisUtils.getInstance().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalysisUtils.getInstance().onPause(this);
    }

    @Override
    public void initIntent(Intent intent) {

    }

    @Override
    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(this, cla));
    }

    @Override
    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
    }

    @Override
    public void toast(String msg) {
        ToastUtils.show(this, msg);
    }

    @Override
    public void toast(int msgId) {
        ToastUtils.show(this, msgId);
    }

    @Override
    public void toastLong(String msg) {
        ToastUtils.showLong(this, msg);
    }

    @Override
    public void toastLong(int msgId) {
        ToastUtils.showLong(this, msgId);
    }

    @Override
    public void log(Object msg) {
        Logger.d(msg);
    }

    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
