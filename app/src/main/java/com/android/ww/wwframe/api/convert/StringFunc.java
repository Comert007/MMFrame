package com.android.ww.wwframe.api.convert;


import com.android.ww.wwframe.exception.ApiException;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * Created by 10142 on 2016/10/10.
 */
public class StringFunc implements Func1<ResponseBody, String> {
    @Override
    public String call(ResponseBody responseBody) {
        if (responseBody != null) {
            try {
                return responseBody.string();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ApiException("数据转换出错，请联系开发！");
            }
        } else {
            throw new ApiException("获取数据失败，请检查网络是否正常！");
        }
    }
}
