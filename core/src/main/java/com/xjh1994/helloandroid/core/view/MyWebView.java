package com.xjh1994.helloandroid.core.view;

/**
 * Created by xjh1994 on 2015/11/5.
 */

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xjh1994.helloandroid.core.R;

public class MyWebView extends WebView {

    private ProgressBar progressbar;
    private Context mContext;

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context.getApplicationContext();
        progressbar = (ProgressBar) LayoutInflater.from(mContext).inflate(R.layout.view_progressbar, null);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 6, 0, 0));
        addView(progressbar);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(mContext, error.getDescription(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(mContext, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        getSettings().setBuiltInZoomControls(true);
        getSettings().setUseWideViewPort(true);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

}


