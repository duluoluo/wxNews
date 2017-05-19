package com.wxandroid.common.injector.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wenxin
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

}
