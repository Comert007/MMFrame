package com.android.ww.wwframe.api.convert;


import com.android.ww.wwframe.exception.ApiException;

import java.io.Reader;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * Created by feng on 2016/10/10.
 */
public class CharStreamFunc implements Func1<ResponseBody, Reader> {
    @Override
    public Reader call(ResponseBody responseBody) {
        if (responseBody != null)
            return responseBody.charStream();
        else
            throw new ApiException("获取数据失败，请检查网络是否正常！");
    }
}