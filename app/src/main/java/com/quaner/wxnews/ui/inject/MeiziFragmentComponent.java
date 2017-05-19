package com.quaner.wxnews.ui.inject;


import com.quaner.wxnews.common.AppComponent;
import com.quaner.wxnews.ui.fragment.MeiziFragment;
import com.quaner.wxnews.ui.presenter.impl.MeiziPresenterImpl;
import com.wxandroid.common.injector.scope.FragmentScope;

import dagger.Component;

/**
 * Created by wenxin
 */
@FragmentScope
@Component(dependencies = {AppComponent.class})
public interface MeiziFragmentComponent {
    void injectMeizi(MeiziFragment fragment);

    MeiziPresenterImpl getMeiziPresenterImpl();
}