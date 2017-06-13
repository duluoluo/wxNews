package com.quaner.wxnews.mvp.presenter;

import com.quaner.wxnews.mvp.contract.IosContract;
import com.quaner.wxnews.mvp.model.IosModel;
import com.quaner.wxnews.mvp.ui.entity.GankEntity;
import com.wxandroid.common.http.Callback;
import com.wxandroid.common.http.HttpResult;
import com.wxandroid.common.mvp.BasePresenter;
import com.wxandroid.common.utils.AppUtils;
import com.wxandroid.common.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by wenxin
 */
public class IosPresenter extends BasePresenter<IosModel, IosContract.View> {


    @Inject
    public IosPresenter(IosModel model, IosContract.View view) {
        super(model, view);
    }

    public void getIosDatas(String type, final int page, final int flag) {

        LoadingInvoke(mModel.getIosData(type, page), new Callback<HttpResult<List<GankEntity>>>() {
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