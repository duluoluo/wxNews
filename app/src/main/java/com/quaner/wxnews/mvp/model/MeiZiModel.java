package com.quaner.wxnews.mvp.model;

import com.quaner.wxnews.http.utils.ServiceManager;
import com.quaner.wxnews.mvp.contract.MeiZiContract;
import com.quaner.wxnews.mvp.ui.entity.GankEntity;
import com.wxandroid.common.http.HttpResult;
import com.wxandroid.common.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by hasee on 2017/5/11.
 */

public class MeiZiModel extends BaseModel implements MeiZiContract.Model {

    private ServiceManager mServiceManager;

    @Inject
    public MeiZiModel(ServiceManager serviceManager) {
        mServiceManager = serviceManager;
    }


    @Override
    public Observable<HttpResult<List<GankEntity>>> getMeiziData(String type, int page) {
        return mServiceManager.getCommonService().getCommonData(type, page);
    }
}
