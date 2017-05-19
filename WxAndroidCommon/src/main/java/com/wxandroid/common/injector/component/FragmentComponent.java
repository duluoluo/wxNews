package com.wxandroid.common.injector.component;

import android.app.Activity;

import com.wxandroid.common.injector.module.FragmentModule;
import com.wxandroid.common.injector.scope.FragmentScope;

import dagger.Component;

/**
 * Created by wenxin
 */
@FragmentScope
@Component(dependencies = BaseComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

}
