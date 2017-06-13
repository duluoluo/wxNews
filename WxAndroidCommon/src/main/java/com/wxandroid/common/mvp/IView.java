package com.wxandroid.common.mvp;

import android.content.Intent;

/**
 * Created by hasee on 2017/4/17.
 */

public interface IView {

    /**
     * 跳转activity
     */
    void launchActivity(Intent intent);

    /**
     * 杀死自己
     */
    void killMyself();

}
