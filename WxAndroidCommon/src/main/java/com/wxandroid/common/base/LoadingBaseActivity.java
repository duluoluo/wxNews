package com.wxandroid.common.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.wxandroid.common.http.Stateful;
import com.wxandroid.common.mvp.BasePresenter;
import com.wxandroid.common.widget.LoadingPage;
import com.zhy.autolayout.AutoLayoutActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by wenxin
 */
public abstract class LoadingBaseActivity<P extends BasePresenter> extends AutoLayoutActivity implements Stateful {

    @Inject
    protected P mPresenter;
    protected FrameLayout fl_content;
    protected LoadingPage mLoadingPage;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(layoutId());
        super.onCreate(savedInstanceState);
        initInject();
        initView();
        fl_content = (FrameLayout) findViewById(setFrameLayoutId());
        if (mLoadingPage == null) {
            mLoadingPage = new LoadingPage(this) {
                @Override
                protected void initView() {
                    bind = ButterKnife.bind(LoadingBaseActivity.this);
                    LoadingBaseActivity.this.initUi();
                }

                @Override
                protected void loadData() {
                    LoadingBaseActivity.this.loadData();
                }

                @Override
                protected int getLayoutId() {
                    return LoadingBaseActivity.this.contentLayoutId();
                }
            };
        }
        fl_content.addView(mLoadingPage);
        loadData();
    }

    //子类findViewById
    protected abstract void initView();

    //初始化控件 setAdapter等放在此方法。
    protected abstract void initUi();

    //联网获取数据根据状态显示界面，如不需联网在此方法设置Constants.STATE_SUCCESS
    protected abstract void loadData();

    //注入依赖
    protected abstract void initInject();

    //布局id
    protected abstract int layoutId();

    //设置flId
    protected abstract int setFrameLayoutId();

    //联网内容id. 根据状态显示
    protected abstract int contentLayoutId();

    @Override
    public void setState(int state) {
        mLoadingPage.state = state;
        mLoadingPage.showPage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (bind != null) {
            bind.unbind();
        }
    }
}
