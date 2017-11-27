package com.quaner.wxnews.di.component;


import com.quaner.wxnews.di.module.IosModule;
import com.quaner.wxnews.ui.fragment.IosFragment;
import com.wxandroid.common.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by wenxin
 */
@FragmentScope
@Component(modules = {IosModule.class},dependencies = {AppComponent.class})
public interface IosFragmentComponent {
    void injectIos(IosFragment activity);
}