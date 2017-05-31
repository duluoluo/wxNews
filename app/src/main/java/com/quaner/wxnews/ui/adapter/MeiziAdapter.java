package com.quaner.wxnews.ui.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quaner.wxnews.R;
import com.quaner.wxnews.ui.entity.GankEntity;
import com.wxandroid.common.CommonApplication;
import com.wxandroid.common.utils.DensityUtil;
import com.wxandroid.common.widget.ScaleImageView;

import java.util.List;

/**
 * Created by wenxin
 */
public class MeiziAdapter extends BaseQuickAdapter<GankEntity, BaseViewHolder> {

    private int screenWidth;

    public MeiziAdapter(@Nullable List<GankEntity> data) {
        super(R.layout.item_meizi, data);
        screenWidth = DensityUtil.getWidth(CommonApplication.getContext());
    }

    @Override
    protected void convert(BaseViewHolder helper, final GankEntity item) {
        final ScaleImageView imageView = helper.getView(R.id.iv_photo);
        final CardView cardView = helper.getView(R.id.cardView);
        if (item.getItemHeight() > 0) {
            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
            layoutParams.height = item.getItemHeight();
        }

        Glide.with(mContext)
                .load(item.getUrl())
                .asBitmap()
                .placeholder(R.color.white)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>(screenWidth / 2, screenWidth / 2) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        //计算高宽比
                        int finalHeight = (screenWidth / 2) * height / width;
                        if (item.getItemHeight() <= 0) {
                            item.setItemHeight(finalHeight);
                            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                            layoutParams.height = item.getItemHeight();
                        }

                        imageView.setImageBitmap(resource);
                    }
                });

    }
}
