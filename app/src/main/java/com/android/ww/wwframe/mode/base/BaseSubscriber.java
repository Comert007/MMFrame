package com.android.ww.wwframe.mode.base;

import android.content.Context;
import android.content.DialogInterface;

import com.android.ww.wwframe.R;
import com.android.ww.wwframe.bean.ResponseBean;
import com.android.ww.wwframe.exception.ApiException;
import com.android.ww.wwframe.utils.DialogUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by feng on 2017/1/24.
 * 自定义Subscriber，对加载进行操作
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;
    private boolean dialogShow;
    private DialogHelper helper;

    public BaseSubscriber(Context context, boolean dialogShow) {
        this.context = context;
        this.dialogShow = dialogShow;
        helper = new DialogHelper(context, dialogShow);
    }


    public void showDialog() {
        if (helper != null) {
            helper.show();
        }
    }

    public void dismissDialog() {
        if (helper != null) {
            helper.dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showDialog();
    }

    @Override
    public void onCompleted() {
        dismissDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissDialog();
        onFailure(e);
    }

    @Override
    public void onNext(T t) {
        next(t);
    }

    public abstract void next(T t);


    public void onFailure(Throwable e) {
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            ResponseBean response = apiException.getResponse();

            if (response != null && response.isNeedRelogin()) {
                DialogUtils.showDialog(context, context.getResources().getString(R.string
                        .dialog_title), context.getResources().getString(R.string.dialog_relogin)
                        , context.getResources().getString(R.string.ok), new DialogInterface
                                .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                return;
            }
            DialogUtils.showNotice(context, "提示", apiException.getMessage());

        }else if (e instanceof SocketTimeoutException){
            DialogUtils.showNotice(context, "提示", "网络请求超时！");
        }else if (e instanceof ConnectException){
            DialogUtils.showNotice(context, "提示", "无法连接到服务器，请检查网络是否正常或请稍后再试！");
        }else{
            DialogUtils.showNotice(context, "提示", "请求失败！");
        }

        e.printStackTrace();
    }

}
