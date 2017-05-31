package com.quaner.wxnews.ui.presenter.impl;


import com.quaner.wxnews.http.utils.ServiceManager;
import com.quaner.wxnews.ui.entity.GankEntity;
import com.quaner.wxnews.ui.presenter.IMeiziPresenter;
import com.wxandroid.common.base.BasePresenterImpl;
import com.wxandroid.common.http.utils.Callback;
import com.wxandroid.common.http.utils.HttpResult;
import com.wxandroid.common.injector.scope.FragmentScope;
import com.wxandroid.common.utils.AppUtils;
import com.wxandroid.common.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by wenxin
 */
@FragmentScope
public class MeiziPresenterImpl extends BasePresenterImpl<IMeiziPresenter.View> implements IMeiziPresenter.Presenter {

    private ServiceManager mServiceManager;

    @Inject
    public MeiziPresenterImpl(ServiceManager mServiceManager) {
        this.mServiceManager = mServiceManager;
    }


    @Override
    public void getMeiziData(String type, final int page, final int flag) {
        //获取妹子数据

        LoadingInvoke(mServiceManager.getCommonService()
                .getCommonData(type, page), new Callback<HttpResult<List<GankEntity>>>() {
            @Override
            protected void subscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            protected void onFailure() {
                mView.hideLoadingMore(true);
            }

            @Override
            protected void onSuccess(HttpResult<List<GankEntity>> result) {
                List<GankEntity> datas = result.getResults();
                if (AppUtils.isEmpty(datas)
                        || datas.size() == 0) {
                    mView.hideLoadingMore(false);
                    return;
                }

                if (flag == Constants.ADD) {
                    mView.addData(datas);
                } else {
                    mView.setData(datas);
                }

            }
        });
    }
}