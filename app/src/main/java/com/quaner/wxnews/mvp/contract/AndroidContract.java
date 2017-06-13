package com.quaner.wxnews.mvp.contract;

import com.quaner.wxnews.mvp.ui.entity.GankEntity;
import com.wxandroid.common.mvp.IModel;
import com.wxandroid.common.mvp.IView;

import java.util.List;
/**
 * Created by wenxin
 */
public interface AndroidContract {

    interface View extends IView{
        void hideLoading();

        void hideLoadingMore(boolean isFail);

        void setData(List<GankEntity> results);

        void addData(List<GankEntity> results);
    }

    interface Model extends IModel{

    }
}