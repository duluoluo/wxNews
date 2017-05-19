package com.quaner.wxnews.ui.presenter.impl;

import com.quaner.wxnews.http.utils.ServiceManager;
import com.quaner.wxnews.ui.entity.GankEntity;
import com.quaner.wxnews.ui.presenter.IAndroidPresenter;
import com.wxandroid.common.base.BasePresenterImpl;
import com.wxandroid.common.http.utils.Callback;
import com.wxandroid.common.http.utils.HttpResult;
import com.wxandroid.common.utils.AppUtils;
import com.wxandroid.common.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by wenxin
 */
public class AndroidPresenterImpl extends BasePresenterImpl<IAndroidPresenter.View> implements IAndroidPresenter.Presenter {

    private ServiceManager mServiceManager;

    @Inject
    public AndroidPresenterImpl(ServiceManager mServiceManager) {
        this.mServiceManager = mServiceManager;
    }


    public void getAndroidDatas(String type, int page, final int flag) {

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
                mView.hideLoading();
                if (flag == Constants.ADD) {
                    mView.addData(datas);
                } else {
                    mView.setData(datas);
                }
            }
        });
    }
}