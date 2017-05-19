package com.wxandroid.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wenxin
 */
public abstract class BaseFragment<P extends BasePresenterImpl> extends Fragment {
    @Inject
    protected P mPresenter;
    public View mFragmentView;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.setView(this);
        }
        initInject();
        if (null == mFragmentView) {
            mFragmentView = View.inflate(getActivity(), getLayoutId(), null);
            bind = ButterKnife.bind(this, mFragmentView);
            init();
            loadData();
        } else {
            bind = ButterKnife.bind(this, mFragmentView);
        }
        return mFragmentView;
    }

    /**
     * 1
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    protected abstract void loadData();

    protected abstract void init();

    protected abstract int getLayoutId();

    /**
     * dagger2注入
     */
    protected abstract void initInject();

    @Override
    public void onDetach() {
        super.onDetach();
        if (null != mFragmentView) {
            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
        }
        if (bind != null) {
            bind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
