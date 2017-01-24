package com.android.ww.wwframe.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.ww.wwframe.R;
import com.android.ww.wwframe.base.titlebar.TitleBar;
import com.android.ww.wwframe.config.AppConfig;
import com.android.ww.wwframe.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import ww.com.core.Debug;
import ww.com.core.ScreenUtil;

/**
 * Created by feng on 2017/1/22.
 * activity界面基类
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    protected Context context;
    protected TitleBar titleBar;
    protected BaseApplication baseApplication;
    protected String tag;
    private int REQUEST_CODE_PERMISSION = 0x00099;
    private String[] requestPermissions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        baseApplication = BaseApplication.getInstance();
        baseApplication.addRunActivity(this);
        tag = this.getClass().getSimpleName();

        if (!ImageLoader.getInstance().isInited()) {
            BaseApplication.initImageLoader(getApplicationContext());
        }

        View viRoot = getLayoutInflater().inflate(getLayoutResId(), null);
        setContentView(viRoot);

        ScreenUtil.init(this, AppConfig.BASE_SCREEN_WIDTH);
        ButterKnife.bind(this);
        scaleView();
        onAttach();
    }

    /**
     * 获取 layout 资源id
     *
     * @return
     */
    protected abstract int getLayoutResId();

    protected void scaleView() {
        ScreenUtil.scale(ButterKnife.findById(this, android.R.id.content));
        View initedTitleBar = ButterKnife.findById(this, R.id.title_bar);
        if (initedTitleBar != null) {
            titleBar = TitleBar.getInstance(this).init(initedTitleBar);
        }
    }

    /**
     * 固定 View, Listener, onAttachData
     */
    protected abstract void onAttach();


    private boolean checkTitleBar() {
        if (titleBar == null) {
            Debug.e("View does not have a title bar");
            throw new RuntimeException("View does not have a title bar");
        } else {
            return true;
        }
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
    @OnClick(R.id.text_title_left)
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

    private List<BackListener> backListeners = new ArrayList<>();

    public static interface BackListener {
        boolean onBackPressed();
    }

    public void addBackListener(BackListener listener) {
        backListeners.add(listener);
    }

    public void removeBackListener(BackListener listener) {
        backListeners.remove(listener);
    }

    @Override
    public void onBackPressed() {
        boolean flag = false;
        for (BackListener backListener : backListeners) {
            boolean process = backListener.onBackPressed();
            if (!flag){
                flag = process;
            }
        }

        if (flag){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        baseApplication.removeRunActivity(this);
    }

    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    // 隐藏软键盘
    public void hideSoftKeyBord(View v) {
        if (v == null)
            return;
        v.clearFocus();
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
