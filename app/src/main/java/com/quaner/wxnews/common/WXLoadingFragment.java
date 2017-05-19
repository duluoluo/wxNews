package com.quaner.wxnews.common;

import com.quaner.wxnews.Application;
import com.wxandroid.common.base.BasePresenterImpl;
import com.wxandroid.common.base.LoadingBaseFragment;

/**
 * Created by wenxin
 */
public abstract class WXLoadingFragment<P extends BasePresenterImpl> extends LoadingBaseFragment<P> {

    protected Application mApplication;

    @Override
    protected void initInject() {
        mApplication = (Application) getActivity().getApplication();
        setupFragmentComponent(mApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupFragmentComponent(AppComponent appComponent);


}
