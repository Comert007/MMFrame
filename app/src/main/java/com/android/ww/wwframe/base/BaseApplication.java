package com.android.ww.wwframe.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.android.ww.wwframe.config.AppConfig;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

import ww.com.core.Debug;
import ww.com.core.ScreenUtil;
import ww.com.core.utils.ACache;
import ww.com.http.OkHttpRequest;

/**
 * Created by feng on 2017/1/22.
 * 程序入口，初始化数据
 */

public class BaseApplication extends Application {

    private ArrayList<Activity> runActivity = new ArrayList<>();
    private static BaseApplication instance;
    private ACache aCache;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ScreenUtil.init(this, AppConfig.BASE_SCREEN_WIDTH);
        OkHttpRequest.setLogging(true);

        initCache();
        initImageLoader(getApplicationContext());
        Debug.setTag("frame");

    }

    public static BaseApplication getInstance() {
        return instance;
    }

    private void initCache() {
        aCache = ACache.get(this);
    }

    public void addRunActivity(Activity _value) {
        if (this.runActivity == null) {
            this.runActivity = new ArrayList<Activity>();
        }
        if (!this.runActivity.contains(_value)) {
            this.runActivity.add(_value);
        }
    }

    public void removeRunActivity(Activity _value) {
        if (this.runActivity != null) {
            this.runActivity.remove(_value);
        }
    }

    public void clearActivities() {
        if (this.runActivity != null) {
            for (Activity act : this.runActivity) {
                act.finish();
            }
        }
    }

    public void exitApp() {
        Debug.d("-------exit app");
        if (this.runActivity != null) {
            for (Activity act : this.runActivity) {
                act.finish();
            }
        }
        System.exit(0);
    }



    public void killAllRunnintActivities() {
        if (this.runActivity != null) {
            for (Activity activity : this.runActivity) {
                activity.finish();
            }
        }
    }

    public ArrayList<Activity> getRunActivity() {
        if (this.runActivity == null) {
            this.runActivity = new ArrayList<Activity>();
        }
        return this.runActivity;
    }


    public boolean isRunning() {
        return runActivity.size() > 0;
    }

    public void setRunActivity(ArrayList<Activity> runActivity) {
        this.runActivity = runActivity;
    }

    public void releaseData() {
        if (this.runActivity != null) {
            this.runActivity.clear();
            this.runActivity = null;
        }
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheExtraOptions(480, 800)
                .discCache(DefaultConfigurationFactory.createDiscCache(context, new
                        Md5FileNameGenerator(), 0, 100))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(150)
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }


    public static DisplayImageOptions getDisplayImageOptions(int drawableRes) {
        return getDisplayImageOptions(drawableRes, drawableRes, drawableRes);
    }

    public static DisplayImageOptions getDisplayImageOptions(int onLoading,
                                                             int emptyUri, int onFail) {
        return getDisplayImageBuilder(onLoading, emptyUri, onFail).build();
    }

    public static DisplayImageOptions.Builder getDisplayImageBuilder(
            int drawableRes) {
        return getDisplayImageBuilder(drawableRes, drawableRes, drawableRes);
    }

    public static DisplayImageOptions.Builder getDisplayImageBuilder(
            int onLoading, int emptyUri, int onFail) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.resetViewBeforeLoading(true).cacheInMemory(true)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .showImageOnLoading(onLoading).showImageForEmptyUri(emptyUri)
                .showImageOnFail(onFail).build();
        return builder;
    }

    public static DisplayImageOptions getDisplayImageOptions(int drawableRes, boolean cacheable) {
        return getDisplayImageOptions(drawableRes, drawableRes, drawableRes, cacheable);
    }

    public static DisplayImageOptions getDisplayImageOptions(int onLoading,
                                                             int emptyUri, int onFail, boolean
                                                                     cacheable) {
        return getDisplayImageBuilder(onLoading, emptyUri, onFail, cacheable).build();
    }

    public static DisplayImageOptions.Builder getDisplayImageBuilder(
            int onLoading, int emptyUri, int onFail, boolean cacheable) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.resetViewBeforeLoading(true).cacheInMemory(cacheable)
                .cacheOnDisc(cacheable).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .showImageOnLoading(onLoading).showImageForEmptyUri(emptyUri)
                .showImageOnFail(onFail).build();
        return builder;
    }

}
