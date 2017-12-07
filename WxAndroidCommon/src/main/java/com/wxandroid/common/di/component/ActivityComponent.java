package com.wxandroid.common.di.component;

import android.app.Activity;

import com.wxandroid.common.di.module.ActivityModule;
import com.wxandroid.common.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
