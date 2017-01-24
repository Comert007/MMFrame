package com.android.ww.wwframe.exception;

import android.text.TextUtils;

import com.android.ww.wwframe.bean.ResponseBean;
import com.android.ww.wwframe.config.Constant;

/**
 * Created by feng on 2017/1/24.
 * 请求异常时的处理
 */

public class ApiException extends RuntimeException {

    private String msg;
    private ResponseBean response;

    public ApiException(String msg) {
        this.msg = msg;
    }

    public ApiException(ResponseBean response) {
        this.response = response;
    }


    @Override
    public String getMessage() {
        if (response == null && !TextUtils.isEmpty(msg)) {
            return msg;
        } else if (response != null && TextUtils.isEmpty(msg)) {
            return response.getMsg();
        } else
            return "请求异常！";
    }

    public ResponseBean getResponse() {
        return response;
    }

    public int getCode() {
        if (response == null&& !TextUtils.isEmpty(msg)) {
            return Constant.CODE_ERROR;
        } else if (response != null && TextUtils.isEmpty(msg)) {
            return response.getCode();
        }else {
            return -2;
        }
    }
}
