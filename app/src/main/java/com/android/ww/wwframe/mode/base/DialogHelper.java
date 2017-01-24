package com.android.ww.wwframe.mode.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.android.ww.wwframe.R;

import ww.com.core.ScreenUtil;

/**
 * Created by feng on 2017/1/24.
 * 请求弹框
 */

public class DialogHelper {

    private ProgressDialog dialog;
    private Context context;

    private boolean isShow; //dialog （true:显示；false:不显示）


    public DialogHelper(Context context, boolean isShow) {
        this.context = context;
        this.isShow = isShow;

        initDialog();
    }

    private void initDialog() {
        if (dialog == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_loading, null);
            ScreenUtil.scale(view);

            dialog = new ProgressDialog(context, R.style.LoadingDialog);
            dialog.setCanceledOnTouchOutside(false); //点击不消失，返回消失
//            dialog.setCancelable(false); //点击或返回都不消失
            show();
            dialog.setContentView(view);
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

}
