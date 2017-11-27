package com.quaner.wxnews.di.module;


import com.quaner.wxnews.mvp.contract.MeiZiContract;
import com.quaner.wxnews.mvp.model.MeiZiModel;
import com.wxandroid.common.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hasee on 2017/5/15.
 */
@Module
public class MeiZiModule {
    private MeiZiContract.View mView;

    public MeiZiModule(MeiZiContract.View view) {
        mView = view;
    }

    @Provides
    @FragmentScope
    MeiZiContract.View provideView() {
        return mView;
    }

    @Provides
    @FragmentScope
    MeiZiContract.Model provideModel(MeiZiModel meiZiModel) {
        return meiZiModel;
    }


}
