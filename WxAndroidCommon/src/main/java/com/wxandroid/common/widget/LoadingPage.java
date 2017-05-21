package com.wxandroid.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wxandroid.common.R;
import com.wxandroid.common.CommonApplication;
import com.wxandroid.common.utils.Constants;

import static com.wxandroid.common.utils.Constants.STATE_EMPTY;
import static com.wxandroid.common.utils.Constants.STATE_LOADING;
import static com.wxandroid.common.utils.Constants.STATE_SUCCESS;

/**
 * Created by wenxin
 */
public abstract class LoadingPage extends FrameLayout {

    public int state = STATE_LOADING;// 默认加载中的状态


    private View loadingView;// 加载中的界面
    private View errorView;// 加载失败的界面
    private View emptyView;// 加载为空的界面

    public View successView;// 加载成功的界面

    private Context mContext;

    public LoadingPage(Context context) {
        super(context);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 将几种不同的布局添加到当前布局中
     */
    private void init() {
        if (loadingView == null) {
            loadingView = createLoadingView();// 创建加载界面
            this.addView(loadingView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (errorView == null) {
            errorView = createErrorView();// 创建加载失败界面
            this.addView(errorView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        if (emptyView == null) {
            emptyView = createEmptyView();// 创建加载为空界面
            this.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        showPage();
    }

    /**
     * 根据不同的状态切换不同的界面
     */
    public void showPage() {
        if (loadingView != null) {
            //  加载中  获取 未知   全部显示
            loadingView.setVisibility(state == STATE_LOADING || state == Constants.STATE_UNKNOWN ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            //
            errorView.setVisibility(state == Constants.STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (emptyView != null) {
            //
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }

        //成功界面
        if (state == STATE_SUCCESS) {
            // 请求成后 添加成功界面
            if (successView == null) {
                successView = View.inflate(CommonApplication.getContext(), getLayoutId(), null);
                this.addView(successView, new ViewGroup.LayoutParams(-1, -1));
                initView();
            } else {
                successView.setVisibility(View.VISIBLE);
            }
        } else {
            if (successView != null) {
                successView.setVisibility(View.INVISIBLE);
            }
        }
    }


    protected abstract void initView();

    /**
     * 创建加载中的view
     *
     * @return
     */
    private View createLoadingView() {
        View view = View.inflate(getContext(), R.layout.state_loading, null);
        return view;
    }

    float downY;

    /**
     * 创建加载失败的view
     *
     * @return
     */
    private View createErrorView() {
        View view = View.inflate(getContext(), R.layout.state_error, null);
        view.findViewById(R.id.ll_error_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重新请求网络数据
                state = Constants.STATE_LOADING;
                showPage();
                loadData();
            }
        });
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });
        return view;
    }


    /***
     * 创建加载为空的界面
     *
     * @return
     */
    private View createEmptyView() {
        View view = View.inflate(getContext(), R.layout.state_empty, null);
        return view;
    }


    /**
     * 请求网络数据
     *
     * @return
     */
    protected abstract void loadData();

    /**
     * 创建成功界面
     *
     * @return
     */
    protected abstract int getLayoutId();
}