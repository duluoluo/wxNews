package com.wxandroid.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.wxandroid.common.injector.component.BaseComponent;
import com.wxandroid.common.injector.component.DaggerBaseComponent;
import com.wxandroid.common.injector.module.AppModule;
import com.wxandroid.common.injector.module.HttpModule;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by wenxin
 */
public class CommonApplication extends Application {

    private static Context mContext;
    private static CommonApplication mInstance;
    private static List<Activity> activityList = new LinkedList<Activity>();
    private HttpModule httpModule;
    private AppModule mAppModule;
    private BaseComponent baseComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null) {
            mInstance = this;
        }
        //提供application
        mAppModule = new AppModule(this);
        httpModule = new HttpModule();
        baseComponent = DaggerBaseComponent
                .builder()
                .appModule(mAppModule)
                .build();

        baseComponent.inject(this);
        mContext = getApplicationContext();

    }


    public AppModule getAppModule() {
        return mAppModule;
    }

    public static Context getContext() {
        return mContext;
    }

    public static CommonApplication getInstance() {
        return mInstance;
    }

    // 添加Activity到容器中
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 删除Activity
    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    // 遍历所有Activity并finish
    public static void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    @Override
    public File getCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir;
            }
        }
        return super.getCacheDir();
    }

    public HttpModule getHttpModule() {
        return httpModule;
    }

}
