package com.wxandroid.common.mvp;

import android.content.Context;
import android.content.Intent;

/**
 * Created by wenxin on 2017/11/27.
 */

public interface IView {

    void launchActivity(Intent intent);

    void killMyself();

    void showLoading(String msg);

    void showLoading(int id);

    void hideLoading();

    Context getCtx();
}
