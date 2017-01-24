package com.android.ww.wwframe.mode.base;

import android.view.View;

import com.android.ww.wwframe.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by feng on 2017/1/23.
 */

public abstract class PresenterActivity<V extends IView, M extends IModel> extends BaseActivity
        implements IPresenter {

    protected V view;
    protected M model;

    public PresenterActivity() {
        PresenterHelper.bind(this);
    }

    @Override
    protected void onAttach() {
        View viRoot = findViewById(android.R.id.content);
        if (view!=null){
            ButterKnife.bind(view,viRoot);
        }

        onAttach(viRoot);
    }

    @Override
    public void onAttach(View viRoot) {
        view.onAttachView(viRoot);
    }

    @Override
    public M getModel() {
        return model;
    }

    @Override
    public IView getViewModule() {
        return view;
    }


    @Override
    public void setModel(IModel iModel) {
        this.model = (M) iModel;
    }

    @Override
    public void setViewModule(IView iView) {
        this.view = (V) iView;
    }

}
