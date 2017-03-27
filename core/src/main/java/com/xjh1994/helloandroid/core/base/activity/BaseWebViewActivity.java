package com.xjh1994.helloandroid.core.base.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;

import com.xjh1994.helloandroid.core.R;
import com.xjh1994.helloandroid.core.util.WebUtils;
import com.xjh1994.helloandroid.core.view.MyWebView;

/**
 * Created by XJH on 16/4/13.
 * Blog: http://www.xjh1994.com
 */
public abstract class BaseWebViewActivity extends BaseActivity {

    protected MyWebView webView;
    protected String url;
    protected String title;
    protected String content;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView() {
        setBackTitle();

        Bundle data = getIntent().getExtras();
        if (null == data) finish();
        url = data.getString("url");
        title = data.getString("title");
        content = data.getString("content");

        setTitle(title);

        webView = (MyWebView) findViewById(R.id.webView);
        initWebViewSettings();
        webView.loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
//        webView.setWebViewClient(new MyWebViewClient());
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);
    }

    @Override
    public void initData() {

    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
//            toast("文件需下载查看，正跳转至浏览器...");
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            webView.loadData("", "text/html; charset=UTF-8", null);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        try {
            if (webView != null) {
                ((ViewGroup) webView.getParent()).removeView(webView);
                webView.destroy();
                webView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebUtils.releaseAllWebViewCallback();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onResume() {// 继承自Activity
        super.onResume();
        webView.onResume();
    }
}
