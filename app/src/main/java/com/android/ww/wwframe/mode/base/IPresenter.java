package com.android.ww.wwframe.mode.base;

import android.view.View;

/**
 * Created by 10142 on 2017/1/23.
 */

public interface IPresenter <V extends IView, M extends IModel>{
    /**
     * 绑定 IView
     * @param viRoot
     */
    void onAttach(View viRoot);

    V getViewModule();

    M getModel();

    void setViewModule(V v);

    void setModel(M m);
}
