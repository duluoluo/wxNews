package com.quaner.wxnews.common;

import com.quaner.wxnews.Application;
import com.wxandroid.common.base.BaseFragment;
import com.wxandroid.common.base.BasePresenterImpl;

/**
 * Created by wenxin
 */
public abstract class WXFragment<P extends BasePresenterImpl> extends BaseFragment<P> {

    protected Application mApplication;

    @Override
    protected void initInject() {
        mApplication = (Application) getActivity().getApplication();
        setupFragmentComponent(mApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupFragmentComponent(AppComponent appComponent);


}