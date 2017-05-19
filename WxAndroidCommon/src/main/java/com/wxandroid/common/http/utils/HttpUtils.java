package com.wxandroid.common.http.utils;

import com.wxandroid.common.http.Stateful;
import com.wxandroid.common.utils.Constants;
import com.wxandroid.common.utils.NetworkUtils;
import com.wxandroid.common.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wenxin
 * contact with yangwenxin711@gmail.com
 */
public class HttpUtils {

    public static <T> void invoke(Stateful stateful, Observable<T> observable, Callback<T> callback) {

        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort("网络连接已断开");
            if (stateful != null) {
                if (callback.mHandler == null)
                    stateful.setState(Constants.STATE_ERROR);
            }
            return;
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }
}
