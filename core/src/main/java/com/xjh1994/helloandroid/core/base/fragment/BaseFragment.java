package com.xjh1994.helloandroid.core.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.xjh1994.helloandroid.core.BuildConfig;
import com.xjh1994.helloandroid.core.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by xjh1994 on 2015/12/28.
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment {

    protected View mContentView;

    public LayoutInflater mInflater;

    private Handler mHandler = new Handler();

    public void runOnWorkThread(Runnable action) {
        new Thread(action).start();
    }

    public void runOnUiThread(Runnable action) {
        mHandler.post(action);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());
        mContentView = inflater.inflate(getLayoutResId(), container, false);

        ButterKnife.bind(this, mContentView);

        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initArguments(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void initArguments(Bundle data) {

    }

    public View findView(int paramInt) {
        return getView().findViewById(paramInt);
    }

    @Override
    public void toast(String msg) {
        ToastUtils.show(getActivity(), msg);
    }

    @Override
    public void toast(int msgId) {
        ToastUtils.show(getActivity(), msgId);
    }

    @Override
    public void toastLong(String msg) {
        ToastUtils.showLong(getActivity(), msg);
    }

    @Override
    public void toastLong(int msgId) {
        ToastUtils.showLong(getActivity(), msgId);
    }

    /**
     * 动画启动页面 startAnimActivity
     *
     * @throws
     */
    @Override
    public void startAnimActivity(Intent intent) {
        getActivity().startActivity(intent);
    }

    @Override
    public void startAnimActivity(Class<?> cla) {
        getActivity().startActivity(new Intent(getActivity(), cla));
    }

    @Override
    public void log(Object msg) {
        Logger.d(msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!BuildConfig.DEBUG) {
            MobclickAgent.onPageStart(this.getClass().toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!BuildConfig.DEBUG) {
            MobclickAgent.onPageEnd(this.getClass().toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
