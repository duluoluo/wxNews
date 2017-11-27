package com.quaner.wxnews.di.component;


import com.quaner.wxnews.di.module.MeiZiModule;
import com.quaner.wxnews.ui.fragment.MeiZiFragment;
import com.wxandroid.common.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by wenxin
 */
@FragmentScope
@Component(modules = {MeiZiModule.class}, dependencies = {AppComponent.class})
public interface MeiziFragmentComponent {
    void injectMeizi(MeiZiFragment fragment);

}