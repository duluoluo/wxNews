package com.quaner.wxnews.mvp.model;

import com.quaner.wxnews.http.utils.ServiceManager;
import com.quaner.wxnews.mvp.contract.IosContract;
import com.quaner.wxnews.ui.entity.GankEntity;
import com.wxandroid.common.http.HttpResult;
import com.wxandroid.common.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by hasee on 2017/5/11.
 */

public class IosModel extends BaseModel implements IosContract.Model {


    private ServiceManager mServiceManager;

    @Inject
    public IosModel(ServiceManager serviceManager) {
        mServiceManager = serviceManager;
    }

    @Override
    public Observable<HttpResult<List<GankEntity>>> getIosData(String type, int page){

        return mServiceManager.getCommonService().getCommonData(type,page);
    }
}
