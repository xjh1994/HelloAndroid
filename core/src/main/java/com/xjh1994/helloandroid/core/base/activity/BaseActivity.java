package com.xjh1994.helloandroid.core.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.logger.Logger;
import com.xjh1994.helloandroid.core.R;
import com.xjh1994.helloandroid.core.util.ActivityUtils;
import com.xjh1994.helloandroid.core.util.ToastUtils;
import com.xjh1994.helloandroid.core.util.analysis.AnalysisUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xjh1994 on 2016/8/24.
 */

public abstract class BaseActivity extends SwipeBackActivity implements IBaseActivity {

    protected InputMethodManager inputMethodManager;

    protected SwipeBackLayout mSwipeBackLayout;

    private boolean eventBusEnabled = false;

    protected Subscription mSubscription;

    protected Unbinder mUnbinder;

    protected ProgressDialog mDialog;

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
        mUnbinder = ButterKnife.bind(this);

        if (eventBusEnabled) {
            EventBus.getDefault().register(this);
        }

        mSwipeBackLayout = getSwipeBackLayout();

        initView();
        initData();
    }

    private void initDialog() {
        mDialog = new ProgressDialog(this);
    }

    public void setRefreshing(boolean isRefreshing) {
        if (isRefreshing) {
            mDialog.show();
        } else {
            mDialog.dismiss();
        }
    }

    private void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void setBackTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    public void setEventBusEnabled() {
        this.eventBusEnabled = true;
    }

    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    /**
     * 隐藏软键盘
     * hideSoftInputView
     *
     * @param
     * @return void
     * @throws
     * @Title: hideSoftInputView
     * @Description: TODO
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
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

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }

        mUnbinder.unbind();

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
