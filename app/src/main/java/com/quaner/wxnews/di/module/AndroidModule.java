package com.quaner.wxnews.di.module;

import com.quaner.wxnews.mvp.contract.AndroidContract;
import com.quaner.wxnews.mvp.model.AndroidModel;
import com.wxandroid.common.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hasee on 2017/5/15.
 */
@Module
public class AndroidModule {

    private AndroidContract.View mView;

    public AndroidModule(AndroidContract.View view) {
        mView = view;
    }

    @Provides
    @FragmentScope
    AndroidContract.View provideView(){
        return mView;
    }

    @Provides
    @FragmentScope
    AndroidContract.Model provideModel(AndroidModel model){
        return model;
    }

}
