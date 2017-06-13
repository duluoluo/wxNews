package com.quaner.wxnews.mvp.ui.activity;

import android.support.v4.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.quaner.wxnews.R;
import com.quaner.wxnews.mvp.ui.entity.GankEntity;
import com.wxandroid.common.base.BaseActivity;

import butterknife.BindView;


/**
 * Created by wenxin
 */
public class MeiziDetailActivity extends BaseActivity {


    @BindView(R.id.photoView)
    PhotoView mPhotoView;
    public static final String TRANSIT_PIC = "picture";

    @Override
    protected void init() {
        ViewCompat.setTransitionName(mPhotoView, TRANSIT_PIC);
        GankEntity mGankEntity = (GankEntity) getIntent().getSerializableExtra("data");
        Glide.with(this).load(mGankEntity.getUrl()).centerCrop().into(mPhotoView);
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_meizi_detail;
    }

}
