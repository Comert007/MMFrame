package com.android.ww.wwframe.utils;

import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by feng on 2017/1/23.
 * 系统webview（安全，兼容）支持4.2
 */

public class WebViewFactory {
    private boolean isSave;
    private boolean isCookie;//cookie缓存

    public static class Builder {
        WebViewFactory factory;
        WebView webView;
        WebSettings webSettings;

        public Builder(WebView webView) {
            if (webView == null)
                throw new RuntimeException("the webView is null");

            this.webView = webView;
            factory = new WebViewFactory();
            this.webSettings = this.webView.getSettings();
        }

        public Builder isSavePass(boolean isSave) {
            factory.isSave = isSave;
            return this;
        }

        public Builder isCookie(boolean isCookie) {
            factory.isCookie = isCookie;
            return this;
        }

        public WebView create() {

            webSettings.setJavaScriptEnabled(true);

            if (factory.isSave) {
                webSettings.setSavePassword(true);
                webSettings.setSaveFormData(true);
            }

            if (factory.isCookie) {
                webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //不保存缓存 模式
            }
            return webView;
        }
    }
}
