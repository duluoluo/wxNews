package com.quaner.wxnews.http.module;

import com.quaner.wxnews.http.service.CommonService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by wenxin
 * contact with yangwenxin711@gmail.com
 */
@Module
public class ServiceModule {

    @Singleton
    @Provides
    CommonService provideCommonService(Retrofit retrofit) {
        return retrofit.create(CommonService.class);
    }

}
