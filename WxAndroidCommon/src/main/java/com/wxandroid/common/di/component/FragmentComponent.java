package com.wxandroid.common.di.component;

import android.app.Activity;

import com.wxandroid.common.di.module.FragmentModule;
import com.wxandroid.common.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by wenxin
 */
@FragmentScope
@Component(dependencies = BaseComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

}
