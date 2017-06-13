package com.quaner.wxnews.common;

import com.quaner.wxnews.http.utils.ServiceManager;
import com.quaner.wxnews.inject.module.ServiceModule;
import com.wxandroid.common.CommonApplication;
import com.wxandroid.common.injector.module.AppModule;
import com.wxandroid.common.injector.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;


/**
 * Created by wenxin
 * contact with yangwenxin711@gmail.com
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class, ServiceModule.class})
public interface AppComponent {

    //application
    CommonApplication WxCoreApplication();

    //retrofit
    Retrofit retrofit();

    //服务管理器,retrofitApi
    ServiceManager serviceManager();

}
