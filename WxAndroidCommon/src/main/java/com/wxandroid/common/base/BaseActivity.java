package com.wxandroid.common.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;

import com.wxandroid.common.mvp.BasePresenter;
import com.wxandroid.common.mvp.IView;
import com.zhy.autolayout.AutoLayoutActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * Created by wenxin
 */
public abstract class BaseActivity<P extends BasePresenter> extends AutoLayoutActivity implements IView {

    @Inject
    protected P mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(layoutId());
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        initInject();
        ButterKnife.bind(this);
        init();
    }

    protected abstract void init();

    protected abstract void initInject();

    protected abstract int layoutId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void showLoading(String message) {
        if (message == null || TextUtils.isEmpty(message)) {
            mProgressDialog.setMessage("正在加载...");
        } else {
            mProgressDialog.setMessage(message);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void showLoading(int id) {
        showLoading(getString(id));
    }

    @Override
    public void hideLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public Context getCtx() {
        return this;
    }
}
