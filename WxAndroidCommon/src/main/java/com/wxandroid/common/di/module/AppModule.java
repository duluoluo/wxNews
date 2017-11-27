package com.wxandroid.common.di.module;

import com.wxandroid.common.CommonApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wenxin
 */
@Module
public class AppModule {

    private CommonApplication application;

    public AppModule(CommonApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    CommonApplication provideApplicationContext() {
        return application;
    }
}
