package com.android.ww.wwframe.exception;

import android.text.TextUtils;

/**
 * Created by feng on 2017/1/23.
 * app 异常类
 */

public class AppException extends RuntimeException{
    private String msg;

    public AppException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AppException(Exception cause) {
        super(cause);
    }


    @Override
    public String getMessage() {
        if (TextUtils.isEmpty(msg))
            return "未知错误";
        else return msg;
    }
}
