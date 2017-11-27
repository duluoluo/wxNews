package com.quaner.wxnews.di.module;

import com.quaner.wxnews.mvp.contract.IosContract;
import com.quaner.wxnews.mvp.model.IosModel;
import com.wxandroid.common.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hasee on 2017/5/15.
 */
@Module
public class IosModule {

    private IosContract.View mView;

    public IosModule(IosContract.View view) {
        mView = view;
    }

    @Provides
    @FragmentScope
    IosContract.View provideView() {
        return mView;
    }

    @Provides
    @FragmentScope
    IosContract.Model provideModel(IosModel model) {
        return model;
    }

}
