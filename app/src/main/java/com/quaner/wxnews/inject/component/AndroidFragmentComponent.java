package com.quaner.wxnews.inject.component;

import com.quaner.wxnews.inject.module.AndroidModule;
import com.quaner.wxnews.mvp.ui.fragment.AndroidFragment;
import com.wxandroid.common.injector.scope.FragmentScope;

import dagger.Component;

/**
 * Created by wenxin
 */
@FragmentScope
@Component(modules = {AndroidModule.class},dependencies = {AppComponent.class})
public interface AndroidFragmentComponent {
    void injectAndroid(AndroidFragment fragment);
}