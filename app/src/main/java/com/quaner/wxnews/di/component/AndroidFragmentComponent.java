package com.quaner.wxnews.di.component;

import com.quaner.wxnews.di.module.AndroidModule;
import com.quaner.wxnews.ui.fragment.AndroidFragment;
import com.wxandroid.common.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by wenxin
 */
@FragmentScope
@Component(modules = {AndroidModule.class},dependencies = {AppComponent.class})
public interface AndroidFragmentComponent {
    void injectAndroid(AndroidFragment fragment);
}