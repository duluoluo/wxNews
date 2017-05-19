package com.wxandroid.common.http.utils;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.wxandroid.common.http.Stateful;
import com.wxandroid.common.utils.LogUtils;
import com.wxandroid.common.utils.Constants;
import com.wxandroid.common.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by wenxin
 * contact with yangwenxin711@gmail.com
 */
public abstract class Callback<T> implements Observer<T> {

    private Context mContext;
    public ProgressDialogHandler mHandler;
    private Stateful target;

    public Callback() {

    }

    public Callback(Context context) {
        this.mContext = context;
        mHandler = new ProgressDialogHandler(context);
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
        LogUtils.e("出错了大兄弟 -------------- " + e.toString());
        onFailure();
        dismissProgressDialog();
    }

    @Override
    public void onSubscribe(Disposable d) {
        showProgressDialog();
        subscribe(d);
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        HttpResult result = (HttpResult) t;
        if (!result.isError()) {//成功
            setState(Constants.STATE_SUCCESS);
            onSuccess(t);
        }
    }

    private void showProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mHandler = null;
        }
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
