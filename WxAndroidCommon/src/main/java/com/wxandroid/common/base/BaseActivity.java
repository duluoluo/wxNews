package com.wxandroid.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.zhy.autolayout.AutoLayoutActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * Created by wenxin
 */
public abstract class BaseActivity<P extends BasePresenterImpl> extends AutoLayoutActivity {

    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(layoutId());
        super.onCreate(savedInstanceState);
        initInject();
        if (mPresenter != null) {
            mPresenter.setView(this);
        }
        ButterKnife.bind(this);
        init();
    }

    protected abstract void init();

    protected abstract void initInject();

    protected abstract int layoutId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
