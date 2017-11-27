package com.quaner.wxnews;


import android.app.Activity;
import android.os.Bundle;

import com.quaner.wxnews.di.component.AppComponent;
import com.quaner.wxnews.di.component.DaggerAppComponent;
import com.quaner.wxnews.di.module.ServiceModule;
import com.wxandroid.common.CommonApplication;

/**
 * Created by wenxin
 */
public class MyApplication extends CommonApplication {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .appModule(getAppModule())
                .httpModule(getHttpModule())
                .serviceModule(new ServiceModule())
                .build();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                removeActivity(activity);
            }
        });
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }


}
