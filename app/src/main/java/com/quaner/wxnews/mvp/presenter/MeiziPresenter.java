package com.quaner.wxnews.mvp.presenter;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.quaner.wxnews.mvp.contract.MeiZiContract;
import com.quaner.wxnews.mvp.model.MeiZiModel;
import com.quaner.wxnews.mvp.ui.entity.GankEntity;
import com.wxandroid.common.CommonApplication;
import com.wxandroid.common.http.Callback;
import com.wxandroid.common.http.HttpResult;
import com.wxandroid.common.injector.scope.FragmentScope;
import com.wxandroid.common.mvp.BasePresenter;
import com.wxandroid.common.utils.AppUtils;
import com.wxandroid.common.utils.Constants;
import com.wxandroid.common.utils.glide.ImageLoader;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenxin
 */
@FragmentScope
public class MeiziPresenter extends BasePresenter<MeiZiModel, MeiZiContract.View> {


    @Inject
    public MeiziPresenter(MeiZiModel model, MeiZiContract.View view) {
        super(model, view);
    }

    public void getMeiziData(String type, final int page, final int flag) {
        //获取妹子数据

        LoadingInvoke(mModel.getMeiziData(type, page), new Callback<HttpResult<List<GankEntity>>>() {
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

                dealData(datas, flag);
            }
        });
    }

    private void dealData(final List<GankEntity> datas, final int flag) {


        Observable.fromIterable(datas)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<GankEntity>() {
                    @Override
                    public void accept(@NonNull final GankEntity zi) throws Exception {
                        Bitmap bitmap = ImageLoader.loadImageBitmap(zi.getUrl(),
                                CommonApplication.getContext());
                        if (bitmap != null) {
                            zi.setItemWidth(bitmap.getWidth());
                            zi.setItemHeight(bitmap.getHeight());
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankEntity value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        setState(Constants.STATE_SUCCESS);
                        if (flag == Constants.ADD) {
                            mView.addData(datas);
                        } else {
                            mView.setData(datas);
                        }
                    }
                });
    }
}