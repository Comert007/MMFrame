package com.android.ww.wwframe.utils;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class CookieUtils {

    /**
     * 清除cookie数据
     */
    public static void clearCookies(Context context) {

        @SuppressWarnings("unused")

        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);

        CookieManager cookieManager = CookieManager.getInstance();

        cookieManager.removeAllCookie();

        CookieSyncManager.getInstance().sync();
    }
}