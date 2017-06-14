package com.quaner.wxnews.inject.component;


import com.quaner.wxnews.inject.module.IosModule;
import com.quaner.wxnews.mvp.ui.fragment.IosFragment;
import com.wxandroid.common.injector.scope.FragmentScope;

import dagger.Component;

/**
 * Created by wenxin
 */
@FragmentScope
@Component(modules = {IosModule.class},dependencies = {AppComponent.class})
public interface IosFragmentComponent {
    void injectIos(IosFragment activity);
}