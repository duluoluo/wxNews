package com.quaner.wxnews.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quaner.wxnews.R;
import com.quaner.wxnews.ui.entity.GankEntity;
import com.wxandroid.common.utils.AppUtils;

import java.util.List;


/**
 * Created by panda.guo on 2017/5/17.
 */

public class IosAdapter extends BaseQuickAdapter<GankEntity, BaseViewHolder> {

    public IosAdapter(@Nullable List<GankEntity> data) {
        super(R.layout.item_android, data);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int positions) {
        super.onBindViewHolder(holder, positions);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GankEntity item) {
        ImageView imageView = (ImageView) helper.getView(R.id.imageView);
        TextView tvTitle = (TextView) helper.getView(R.id.tvTitle);
        TextView tvDesc = (TextView) helper.getView(R.id.tvDesc);
        List<String> images = item.getImages();
        if (!AppUtils.isEmpty(images) && !TextUtils.isEmpty(images.get(0))) {
            Glide.with(mContext).load(images.get(0))
                    .placeholder(R.mipmap.bg_other)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }
        tvTitle.setText(TextUtils.isEmpty(item.getWho()) ? "佚名" : item.getWho());
        tvDesc.setText(item.getDesc());
    }

}
