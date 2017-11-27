package com.wxandroid.common.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.wxandroid.common.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;
/**
 * Created by wenxin
 */
@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }

}
