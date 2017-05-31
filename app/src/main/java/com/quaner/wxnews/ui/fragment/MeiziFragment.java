package com.quaner.wxnews.ui.fragment;


import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quaner.wxnews.R;
import com.quaner.wxnews.common.AppComponent;
import com.quaner.wxnews.common.WXLoadingFragment;
import com.quaner.wxnews.ui.activity.MeiziDetailActivity;
import com.quaner.wxnews.ui.adapter.MeiziAdapter;
import com.quaner.wxnews.ui.entity.GankEntity;
import com.quaner.wxnews.ui.inject.DaggerMeiziFragmentComponent;
import com.quaner.wxnews.ui.presenter.IMeiziPresenter;
import com.quaner.wxnews.ui.presenter.impl.MeiziPresenterImpl;
import com.wxandroid.common.utils.Constants;
import com.wxandroid.common.utils.SpaceItemDecoration;
import com.wxandroid.common.widget.ScaleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wenxin
 */
public class MeiziFragment extends WXLoadingFragment<MeiziPresenterImpl>
        implements IMeiziPresenter.View, SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {


    private static final int MAX_PAGE = 10;
    @BindView(R.id.rl_content)
    RecyclerView rlContent;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    int page = 1;
    private MeiziAdapter mAdapter;
    List<GankEntity> results = new ArrayList<>();
    private StaggeredGridLayoutManager staggeredGridLayoutManager;


    @Override
    protected void loadData() {
        mPresenter.getMeiziData("福利", page, Constants.INIT);
    }

    @Override
    protected void init() {
        srl.setOnRefreshListener(this);
        srl.setColorSchemeResources(android.R.color.holo_red_dark, R.color.green, android.R.color.holo_blue_dark);
        setAdapter(results);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getMeiziData("福利", page, Constants.INIT);
    }

    @Override
    public void onLoadMoreRequested() {
        rlContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (page >= MAX_PAGE) {
                    mAdapter.loadMoreEnd();
                } else {
                    ++page;
                    mPresenter.getMeiziData("福利", page, Constants.ADD);
                }
            }
        }, 500);

    }


    private void setAdapter(final List<GankEntity> results) {
        if (mAdapter == null) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            rlContent.setLayoutManager(staggeredGridLayoutManager);
            rlContent.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new MeiziAdapter(results);
            mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            mAdapter.setNewData(results);
            mAdapter.setEnableLoadMore(false);
            mAdapter.setOnLoadMoreListener(this, rlContent);
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    int viewPosition = rlContent.getChildAdapterPosition(view);
                    BaseViewHolder viewHolder = (BaseViewHolder) rlContent.findViewHolderForAdapterPosition(position);
                    ScaleImageView imageView = (ScaleImageView) viewHolder.getView(R.id.iv_photo);
                    GankEntity mGankEntity = mAdapter.getData().get(viewPosition);
                    Intent intent = new Intent(getActivity(), MeiziDetailActivity.class);
                    intent.putExtra("data", mGankEntity);
                    ActivityOptionsCompat compat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(getActivity(), imageView, MeiziDetailActivity.TRANSIT_PIC);
                    try {
                        ActivityCompat.startActivity(getActivity(), intent, compat.toBundle());
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        startActivity(intent);
                    }
                }
            });
            rlContent.addItemDecoration(new SpaceItemDecoration(10));
            rlContent.setAdapter(mAdapter);

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meizi;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMeiziFragmentComponent.builder().appComponent(appComponent)
                .build().injectMeizi(this);
    }


    public void hideLoadingMore(boolean isFail) {
        if (mAdapter != null) {
            if (isFail) {
                mAdapter.loadMoreFail();
            } else {
                mAdapter.loadMoreEnd();
            }
            if (srl.isRefreshing()) {
                srl.setRefreshing(false);
            }
        } else {
            setState(Constants.STATE_EMPTY);
        }
    }


    public void hideLoading() {
        if (srl != null)
            if (srl.isRefreshing()) {
                srl.setRefreshing(false);
            }
        if (mAdapter != null)
            mAdapter.loadMoreComplete();
    }


    @Override
    public void setData(List<GankEntity> results) {
        hideLoading();
        mAdapter.setNewData(results);
        mAdapter.disableLoadMoreIfNotFullPage(rlContent);
    }

    @Override
    public void addData(List<GankEntity> results) {
        hideLoading();
        mAdapter.addData(results);
        mAdapter.disableLoadMoreIfNotFullPage(rlContent);
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}
