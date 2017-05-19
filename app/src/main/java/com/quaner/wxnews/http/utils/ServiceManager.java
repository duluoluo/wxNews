package com.quaner.wxnews.http.utils;


import com.quaner.wxnews.http.service.CommonService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by wenxin
 * contact with yangwenxin711@gmail.com
 */
@Singleton
public class ServiceManager {

    private final CommonService mCommonService;


    @Inject
    public ServiceManager(CommonService commonService) {
        this.mCommonService = commonService;
    }

    public CommonService getCommonService() {
        return mCommonService;
    }

}
