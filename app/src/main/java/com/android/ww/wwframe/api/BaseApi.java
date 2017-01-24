package com.android.ww.wwframe.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.android.ww.wwframe.api.convert.ResponseFunc;
import com.android.ww.wwframe.base.BaseApplication;
import com.android.ww.wwframe.bean.ResponseBean;
import com.android.ww.wwframe.config.AppConfig;

import okhttp3.ResponseBody;
import rx.Observable;
import ww.com.core.utils.PhoneUtils;
import ww.com.http.OkHttpRequest;
import ww.com.http.core.AjaxParams;
import ww.com.http.core.RequestConstants;
import ww.com.http.core.RequestMethod;
import ww.com.http.interfaces.DownloadProgressListener;
import ww.com.http.interfaces.UploadProgressListener;
import ww.com.http.rx.StringFunc;

/**
 * Created by feng on 2017/1/23.
 * Api请求初始化
 */

public class BaseApi  {

    public static String getUrl(String action){
//        return String.format("%1$s%2$s", BuildConfig.BASEURL,action);
        return String.format("%1$s%2$s", AppConfig.BASE_URL, action);
    }

    protected static final AjaxParams getAjaxParams(){
        AjaxParams params  = new AjaxParams();
        params.addHeaders(RequestConstants.USER_AGENT, "user-agent;android ww request v2.0;use " +
                "okhttp 3.0");
        params.addHeaders("APP_VERSION", PhoneUtils.getAppVer(BaseApplication.getInstance()));
        params.addHeaders("DEVICE_UUID", PhoneUtils.getDeviceId(BaseApplication.getInstance()));
        params.addHeaders("DEVICE_MODEL", "2");
        params.addHeaders("DEVICE_VERSION", PhoneUtils.getAppOs());
        params.addHeaders("DEVICE_TOKEN", "");
        params.addHeaders("APP_AUTH_IV", AppConfig.APP_AUTH_IV);
        return params;
    }

    //Json
    public static Observable<ResponseBean> json(String url, JSONObject json){
        AjaxParams params =getAjaxParams();
        params.addParametersJson(json);

        return onJson(url,params)
                .map(new StringFunc())
                .map(new ResponseFunc());
    }

    //post
    public static Observable<ResponseBean> post(String url, AjaxParams params){
        return onPost(url, params)
                .map(new StringFunc())
                .map(new ResponseFunc());
    }

    //get
    public static Observable<ResponseBean> get(String url, AjaxParams params){
        return onGet(url, params)
                .map(new StringFunc())
                .map(new ResponseFunc());
    }


    /**
     * @param url    请求的地址
     * @param params 请求的参数
     * @return
     */
    private static final Observable<ResponseBody> onJson(String url,
                                                      AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.JSON);
        return OkHttpRequest.newObservable(params);
    }

    /**
     * @param url    请求的地址
     * @param params 请求的参数
     * @return
     */
    private static final Observable<ResponseBody> onPost(String url,
                                                      AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.POST);
        return OkHttpRequest.newObservable(params);
    }


    /**
     *
     * @param url
     * @param params
     * @return
     */
    private static final Observable<ResponseBody> onGet(String url, AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.GET);
        return OkHttpRequest.newObservable(params);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param params
     * @param progressListener
     * @return
     */
    private static final Observable<ResponseBody> updateFile(String url,
                                                            AjaxParams params,
                                                            @Nullable UploadProgressListener
                                                                    progressListener) {
        params = params.setBaseUrl(url)
                .setUploadProgressListener(progressListener)
                .setRequestMethod(RequestMethod.POST);
        return OkHttpRequest.newObservable(params);
    }


    /**
     * 下载文件
     *
     * @param url
     * @param params
     * @param progressListener
     * @return
     */
    private static final Observable<ResponseBody> downFile(String url, AjaxParams params,
                                                          @NonNull DownloadProgressListener
                                                                  progressListener) {
        params = params.setBaseUrl(url)
                .setDownloadProgressListener(progressListener)
                .setRequestMethod(RequestMethod.GET);
        return OkHttpRequest.newObservable(params);
    }

}
