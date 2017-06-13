package com.quaner.wxnews.inject.component;


import com.quaner.wxnews.common.AppComponent;
import com.quaner.wxnews.inject.module.MeiZiModule;
import com.quaner.wxnews.mvp.ui.fragment.MeiZiFragment;
import com.wxandroid.common.injector.scope.FragmentScope;

import dagger.Component;

/**
 * Created by wenxin
 */
@FragmentScope
@Component(modules = {MeiZiModule.class}, dependencies = {AppComponent.class})
public interface MeiziFragmentComponent {
    void injectMeizi(MeiZiFragment fragment);

}