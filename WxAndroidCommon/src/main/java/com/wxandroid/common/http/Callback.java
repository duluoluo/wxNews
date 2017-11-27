package com.wxandroid.common.http;

import android.os.Bundle;
import android.os.Message;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.wxandroid.common.mvp.IView;
import com.wxandroid.common.utils.Constants;
import com.wxandroid.common.utils.LogUtils;
import com.wxandroid.common.utils.ProgressDialogHandler;
import com.wxandroid.common.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by wenxin
 */
public abstract class Callback<T> implements Observer<T> {

    private IView view;
    private String msg;
    private int id = -1;
    public ProgressDialogHandler mHandler;
    private Stateful target;

    public Callback() {

    }


    public Callback(IView view, String msg) {
        this.view = view;
        this.msg = msg;
        mHandler = new ProgressDialogHandler(view);
    }

    public Callback(IView view, int id) {
        this.view = view;
        this.id = id;
        mHandler = new ProgressDialogHandler(view);
    }

    public void setTarget(Stateful target) {
        this.target = target;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ConnectException) {
            ToastUtils.showShort("当前网络不可用，请检查网络后重试");
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort("请求超时，请稍后重试");
        } else if (e instanceof HttpException) {
            ToastUtils.showShort("服务器繁忙");
        } else {
            ToastUtils.showShort("服务器繁忙");
        }

        setState(Constants.STATE_ERROR);
        LogUtils.e("onError" + e.toString());
        onFailure();
        if (view != null) {
            hideLoading();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (view != null) {
            if (id == -1)
                showLoading(msg);
            else
                showLoading(id);
        }
        subscribe(d);
    }

    @Override
    public void onComplete() {
        if (view != null) {
            hideLoading();
        }
    }

    @Override
    public void onNext(T t) {
        HttpResult result = (HttpResult) t;
        if (!result.isError()) {//成功
            setState(Constants.STATE_SUCCESS);
            onSuccess(t);
        }
    }


    private void showLoading(int id) {
        Message message = mHandler.obtainMessage(ProgressDialogHandler.SHOW_ID_DIALOG);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        message.setData(bundle);
        message.sendToTarget();
    }

    private void showLoading(String msg) {
        Message message = mHandler.obtainMessage(ProgressDialogHandler.SHOW_MSG_DIALOG);
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        message.setData(bundle);
        message.sendToTarget();
    }


    private void hideLoading() {
        mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        mHandler = null;
        view = null;
    }

    public void setState(int state) {
        if (mHandler == null)
            if (target != null)
                target.setState(state);
    }

    protected abstract void subscribe(Disposable d);

    protected abstract void onFailure();

    protected abstract void onSuccess(T t);
}
