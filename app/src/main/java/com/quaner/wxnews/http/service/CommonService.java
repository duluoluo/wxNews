package com.quaner.wxnews.http.service;


import com.quaner.wxnews.ui.entity.GankEntity;
import com.wxandroid.common.http.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wenxin
 * contact with yangwenxin711@gmail.com
 */
public interface CommonService {

    //    http://gank.io/api/data/Android/10/1
    //    http://gank.io/api/data/福利/10/1
    //    http://gank.io/api/data/iOS/20/2
    //    http://gank.io/api/data/all/20/2
    @GET("data/{type}/20/{page}")
    Observable<HttpResult<List<GankEntity>>> getCommonData(@Path("type") String type, @Path("page") int page);

}

