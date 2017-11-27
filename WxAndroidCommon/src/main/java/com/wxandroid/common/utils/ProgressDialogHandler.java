package com.wxandroid.common.utils;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wxandroid.common.mvp.IView;


/**
 * Created by wenxin
 */
public class ProgressDialogHandler extends Handler {


    public static final int SHOW_MSG_DIALOG = 1;
    public static final int SHOW_ID_DIALOG = 2;
    public static final int DISMISS_PROGRESS_DIALOG = 3;

    private ProgressDialog pd;

    private IView view;

    public ProgressDialogHandler(IView view) {
        super();
        this.view = view;
    }

    @Override
    public void handleMessage(Message msg) {
        Bundle data = null;
        switch (msg.what) {
            case SHOW_MSG_DIALOG:
                data = msg.getData();
                showLoading(data.getString("msg"));
                break;
            case SHOW_ID_DIALOG:
                data = msg.getData();
                showLoading(data.getInt("id"));
                break;
            case DISMISS_PROGRESS_DIALOG:
                hideLoading();
                break;
        }
    }

    public void showLoading(String msg) {
        view.showLoading(msg);
    }

    public void showLoading(int id) {
        view.showLoading(id);
    }

    public void hideLoading() {
        view.hideLoading();
        view = null;
    }
}
