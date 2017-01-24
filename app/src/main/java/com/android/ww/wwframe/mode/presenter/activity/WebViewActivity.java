package com.android.ww.wwframe.mode.presenter.activity;

import android.view.View;
import android.webkit.WebView;

import com.android.ww.wwframe.R;
import com.android.ww.wwframe.mode.base.PresenterActivity;
import com.android.ww.wwframe.mode.base.VoidModel;
import com.android.ww.wwframe.mode.base.VoidView;
import com.android.ww.wwframe.utils.CookieUtils;
import com.android.ww.wwframe.utils.WebViewFactory;
import com.android.ww.wwframe.web.HostJsScope;

import butterknife.BindView;
import cn.pedant.SafeWebViewBridge.InjectedChromeClient;

/**
 * Created by feng on 2017/1/23.
 * web页面
 */

public class WebViewActivity extends PresenterActivity<VoidView,VoidModel> {
    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_webview;
    }

    @Override
    public void onAttach(View viRoot) {
        super.onAttach(viRoot);
        initWeb();
    }

    private void initWeb(){

        webView = new WebViewFactory.Builder(webView)
                .isCookie(true)
                .isSavePass(false)
                .create();

        webView.setWebChromeClient( new InjectedChromeClient("HostApp", HostJsScope.class));
        webView.loadUrl("file:///android_asset/test.html");
    }


    private void onLoad(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CookieUtils.clearCookies(context);
    }
}
