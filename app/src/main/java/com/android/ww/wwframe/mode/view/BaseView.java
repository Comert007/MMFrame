package com.android.ww.wwframe.mode.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.ww.wwframe.mode.base.IView;

/**
 * Created by feng on 2017/1/24.
 */

public abstract class BaseView implements IView {
    protected View rootView;
    protected Context context;

    @Override
    public void onAttachView(@NonNull View view) {
        this.rootView = view;
        context = view.getContext();
        attachView(view);
    }

    public abstract void attachView(@NonNull View view);

    public View getRootView() {
        return rootView;
    }

}
