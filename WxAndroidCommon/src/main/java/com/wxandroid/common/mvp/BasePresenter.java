package com.wxandroid.common.mvp;

import com.wxandroid.common.http.Stateful;
import com.wxandroid.common.http.Callback;
import com.wxandroid.common.http.HttpUtils;
import com.wxandroid.common.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenxin
 */

public class BasePresenter<M extends IModel, V extends IView> implements IPreseter {

    protected final String TAG = this.getClass().getSimpleName();
    protected M mModel;
    protected V mView;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(M model, V view) {
        this.mModel = model;
        this.mView = view;
        onStart();
    }

    public BasePresenter(V rootView) {
        this.mView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }


    //loadingPage
    protected <T> void invoke(Observable<T> observable, Callback<T> callback) {
        callback.setTarget((Stateful) mView);
        HttpUtils.invoke((Stateful) mView, observable, callback);
    }

    /**
     * 检查返回数据是否为空
     */
    public void checkDatas(List datas) {
        if (mView instanceof Stateful)
            if (datas == null) {
                ((Stateful) mView).setState(Constants.STATE_EMPTY);
            } else {
                if (datas.size() == 0) {
                    ((Stateful) mView).setState(Constants.STATE_EMPTY);
                } else {
                    ((Stateful) mView).setState(Constants.STATE_SUCCESS);
                }
            }
    }

    public void setState(int state) {
        if (mView instanceof Stateful) {
            ((Stateful) mView).setState(state);
        }
    }


    @Override
    public void onStart() {
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);//注册eventbus
    }

    @Override
    public void onDestroy() {
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);//解除注册eventbus
        unSubscribe();//解除订阅
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }

    protected boolean useEventBus() {
        return false;
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有subscription放入,集中处理
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
        }
    }
}
