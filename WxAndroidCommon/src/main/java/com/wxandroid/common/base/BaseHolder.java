package com.wxandroid.common.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by wenxin
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final Context mContext;
    protected OnViewClickListener mOnViewClickListener = null;
    protected final String TAG = this.getClass().getSimpleName();
    public BaseHolder(View itemView,Context context) {
        super(itemView);
        this.mContext = context;
        itemView.setOnClickListener(this);//点击事件
        ButterKnife.bind(this,itemView);
        AutoUtils.autoSize(itemView);//适配
    }

    @Override
    public void onClick(View view) {

        if (mOnViewClickListener != null) {
            mOnViewClickListener.onViewClick(view, this.getPosition());
        }
    }
    /**
     * 设置数据
     * 刷新界面
     *
     * @param
     */
    public abstract void setData(List<T> datas, int position);

    public interface OnViewClickListener {
        void onViewClick(View view, int position);
    }

    public void setOnItemClickListener(OnViewClickListener listener) {
        this.mOnViewClickListener = listener;
    }




}
