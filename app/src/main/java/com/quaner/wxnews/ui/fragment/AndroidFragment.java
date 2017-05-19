package com.quaner.wxnews.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quaner.wxnews.R;
import com.quaner.wxnews.common.AppComponent;
import com.quaner.wxnews.common.WXLoadingFragment;
import com.quaner.wxnews.ui.adapter.AndroidAdapter;
import com.quaner.wxnews.ui.entity.GankEntity;
import com.quaner.wxnews.ui.inject.DaggerAndroidFragmentComponent;
import com.quaner.wxnews.ui.presenter.IAndroidPresenter;
import com.quaner.wxnews.ui.presenter.impl.AndroidPresenterImpl;
import com.wxandroid.common.base.WebViewActivity;
import com.wxandroid.common.utils.AppUtils;
import com.wxandroid.common.utils.Constants;
import com.wxandroid.common.utils.SpaceItemDecoration;
import com.wxandroid.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wenxin
 */
public class AndroidFragment extends WXLoadingFragment<AndroidPresenterImpl>
        implements IAndroidPresenter.View
        , SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private static final int MAX_PAGE = 10;
    @BindView(R.id.rl_content)
    RecyclerView rlContent;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    int page = 1;
    private AndroidAdapter mAdapter;
    List<GankEntity> results = new ArrayList<>();

    @Override
    public void setData(List<GankEntity> results) {
        mAdapter.setNewData(results);
    }

    @Override
    public void addData(List<GankEntity> results) {
        mAdapter.addData(results);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getAndroidDatas("Android", page, Constants.INIT);
    }


    @Override
    public void onLoadMoreRequested() {
        if (page >= MAX_PAGE) {
            mAdapter.loadMoreEnd();
        } else {
            ++page;
            mPresenter.getAndroidDatas("Android", page, Constants.ADD);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GankEntity gankEntity = (GankEntity) adapter.getData().get(position);
        String url = gankEntity.getUrl();
        if (AppUtils.isNotEmpty(url)) {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("title", gankEntity.getDesc());
            intent.putExtra("color", android.R.color.holo_blue_light);
            startActivity(intent);
        } else {
            ToastUtils.showShort("暂无资源");
        }
    }

    @Override
    protected void loadData() {

        mPresenter.getAndroidDatas("Android", page, Constants.INIT);
    }


    @Override
    protected void init() {
        srl.setOnRefreshListener(this);
        srl.setColorSchemeResources(android.R.color.holo_red_dark, R.color.green, android.R.color.holo_blue_dark);
        setAdapter(results);
    }

    private void setAdapter(List<GankEntity> results) {
        mAdapter = new AndroidAdapter(results);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setNewData(results);
        mAdapter.setOnLoadMoreListener(this, rlContent);
        mAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlContent.setItemAnimator(new DefaultItemAnimator());
        rlContent.addItemDecoration(new SpaceItemDecoration(15));
        rlContent.setLayoutManager(layoutManager);
        rlContent.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meizi;
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
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAndroidFragmentComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .injectMeizi(this);
    }


}
