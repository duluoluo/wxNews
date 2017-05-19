package com.wxandroid.common.injector.component;

import android.app.Activity;

import com.wxandroid.common.injector.module.ActivityModule;
import com.wxandroid.common.injector.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = BaseComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
