package com.android.ww.wwframe.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.ww.wwframe.R;
import com.android.ww.wwframe.base.titlebar.TitleBar;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import ww.com.core.ScreenUtil;

/**
 * Created by 10142 on 2017/1/23.
 */

public abstract class BaseFragment extends RxFragment implements BaseActivity.BackListener{

    public String tag;
    protected BaseActivity activity;
    protected Context context;
    protected TitleBar titleBar;
    protected View contentView;

    protected boolean pause = false;
    protected Handler handler;

    protected abstract int getLayoutResId();

    protected abstract void onAttach();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        tag = this.getClass().getSimpleName();
        if (contentView ==null) {
            contentView = inflater.inflate(getLayoutResId(), container, false);
        }
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent!=null){
            parent.removeView(contentView);
        }

        initTitle();
        ScreenUtil.scale(contentView);

        ButterKnife.bind(this,contentView);
        onAttach();

        return contentView;
    }

    protected void initTitle() {
        if (contentView != null) {
            View initedTitleBar = ButterKnife.findById(contentView, R.id.title_bar);
            if (initedTitleBar != null) {
                titleBar = TitleBar.getInstance(context).init(initedTitleBar);
            }
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseActivity) activity;
        this.activity.addBackListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        handler = new Handler();
    }

    protected void setTitle(String title) {
        checkTitleBar();
        titleBar.setTitle(title);
    }

    protected Button getTitleButtonLeft(int resId) {
        checkTitleBar();
        return titleBar.getTitleButtonLeft(resId);
    }

    protected Button getTitleButtonRight(int resId) {
        checkTitleBar();
        return titleBar.getTitleButtonRight(resId);
    }

    protected TextView getTitleTextRight(String string) {
        checkTitleBar();
        return titleBar.getTitleTextRight(string);
    }

    protected TextView getTitleTextLeft(String string) {
        checkTitleBar();
        return titleBar.getTitleTextLeft(string);
    }

    @Optional
    @OnClick(R.id.text_title)
    protected void onTitleClick(TextView v) {

    }

    @Optional
    @OnClick(R.id.text_title_right)
    protected void onTitleTextRightClick(TextView v) {

    }

    @Optional
    @OnClick(R.id.text_title_right)
    protected void onTitleTextLeftClick(TextView v) {

    }

    @Optional
    @OnClick(R.id.btn_title_left)
    protected void onTitleButtonLeftClick(Button button) {

    }

    @Optional
    @OnClick(R.id.btn_title_right)
    protected void onTitleButtonRightClick(Button button) {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser){
            pause = true;
        }
    }

    // 隐藏软键盘
    public void hideSoftKeyBord(View v) {
        if (v == null)
            return;
        v.clearFocus();
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void showToast(String msg) {
        activity.showToast(msg);
    }



    private boolean checkTitleBar() {
        if (titleBar == null) {
            throw new RuntimeException("View does not have a title bar");
        } else {
            return true;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        pause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        pause = false;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (activity != null) {
            activity.removeBackListener(this);
        }
        if (contentView != null) {
            try {
                ((ViewGroup) contentView.getParent()).removeView(contentView);
            } catch (Exception e) {
            }
        }
    }
}
