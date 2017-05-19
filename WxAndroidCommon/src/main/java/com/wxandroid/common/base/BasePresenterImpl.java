package com.wxandroid.common.base;

import android.content.Context;

import com.wxandroid.common.http.Stateful;
import com.wxandroid.common.http.utils.Callback;
import com.wxandroid.common.http.utils.HttpUtils;
import com.wxandroid.common.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by wenxin
 */
public abstract class BasePresenterImpl<V> {

    public Context mContext;

    protected CompositeDisposable mCompositeDisposable;

    public V mView;

    public BasePresenterImpl() {
        onStart();
    }

    public void setView(V view) {
        mView = view;
    }

    public void onStart() {
        if (useEvenBus()) {
            EventBus.getDefault().register(this);
        }
    }

    //loadingPage
    protected <T> void LoadingInvoke(Observable<T> observable, Callback<T> callback) {
        callback.setTarget((Stateful) mView);
        HttpUtils.invoke((Stateful) mView, observable, callback);
    }

    //dialog  New Callback(mContext);
    protected <T> void invoke(Observable<T> observable, Callback<T> callback) {
        HttpUtils.invoke(null, observable, callback);
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

    /**
     * 使用EventBus则此方法返回true
     *
     * @return
     */
    protected boolean useEvenBus() {
        return false;
    }


    public void onDestroy() {
        if (useEvenBus()) {
            EventBus.getDefault().unregister(this);
        }
        unSubscribe();
        mView = null;
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
        }
    }

}