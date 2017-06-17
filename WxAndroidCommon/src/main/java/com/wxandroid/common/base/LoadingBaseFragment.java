package com.wxandroid.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.wxandroid.common.CommonApplication;
import com.wxandroid.common.http.Stateful;
import com.wxandroid.common.mvp.BasePresenter;
import com.wxandroid.common.widget.LoadingPage;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by wenxin
 */
public abstract class LoadingBaseFragment<P extends BasePresenter> extends Fragment implements Stateful {
    @Inject
    protected P mPresenter;

    public LoadingPage mLoadingPage;

    protected View successView;

    private Unbinder bind;

    NestedScrollView mScrollView;


    private boolean mIsVisible = false;     // fragment是否显示了

    private boolean isPrepared = false;

    private boolean isFirst = true; //只加载一次界面


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mScrollView == null) {
            mScrollView = new NestedScrollView(CommonApplication.getContext());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mScrollView.setLayoutParams(layoutParams);
            mScrollView.setFillViewport(true);
            mLoadingPage = new LoadingPage(CommonApplication.getContext()) {
                @Override
                protected void initView() {

                    if (isFirst) {
                        LoadingBaseFragment.this.successView = this.successView;
                        bind = ButterKnife.bind(LoadingBaseFragment.this, LoadingBaseFragment.this.successView);
                        LoadingBaseFragment.this.init();
                        isFirst = false;
                    }
                }

                @Override
                protected void loadData() {
                    LoadingBaseFragment.this.loadData();
                }

                @Override
                protected int getLayoutId() {
                    return LoadingBaseFragment.this.getLayoutId();
                }
            };
            mScrollView.addView(mLoadingPage,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
        }
        initInject();
        if (successView != null) {
            bind = ButterKnife.bind(this, successView);
        }
        isPrepared = true;
        loadBaseData();
        return mScrollView;
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {//fragment可见
            mIsVisible = true;
            onVisible();
        } else {//fragment不可见
            mIsVisible = false;
        }
    }


    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void onVisible() {
        if (isFirst) {
            //只加载一次
        }
        loadBaseData();
    }


    public void loadBaseData() {
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        loadData();
    }

    protected abstract void init();

    //联网获取数据根据状态显示界面
    protected abstract void loadData();

    //dagger2注入
    protected abstract void initInject();

    //布局id
    protected abstract int getLayoutId();


    @Override
    public void onDetach() {
        super.onDetach();

        if (bind != null) {
            bind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }

        if (null != mScrollView) {
            ViewParent parent = mScrollView.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(mScrollView);
            }
        }
    }

    @Override
    public void setState(int state) {
        if (mLoadingPage != null) {
            mLoadingPage.state = state;
            mLoadingPage.showPage();
        }
    }
}
