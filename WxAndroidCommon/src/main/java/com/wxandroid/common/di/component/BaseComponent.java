package com.wxandroid.common.di.component;


import com.wxandroid.common.CommonApplication;
import com.wxandroid.common.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wenxin
 */
@Singleton
@Component(modules = {AppModule.class})
public interface BaseComponent {
    void inject(CommonApplication application);

}
