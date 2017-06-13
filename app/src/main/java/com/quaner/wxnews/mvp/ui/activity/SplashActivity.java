package com.quaner.wxnews.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.quaner.wxnews.R;
import com.wxandroid.common.base.BaseActivity;
import com.wxandroid.common.widget.CircleProgressbar;

import butterknife.BindView;


/**
 * Created by wenxin
 */
public class SplashActivity extends BaseActivity {


    @BindView(R.id.tv_skip)
    CircleProgressbar mCircleProgressbar;

    @Override
    protected void init() {
        mCircleProgressbar.setOutLineColor(Color.TRANSPARENT);
        mCircleProgressbar.setInCircleColor(Color.parseColor("#505559"));
        mCircleProgressbar.setProgressColor(Color.parseColor("#1BB079"));
        mCircleProgressbar.setProgressLineWidth(5);
        mCircleProgressbar.setProgressType(CircleProgressbar.ProgressType.COUNT);
        mCircleProgressbar.setTimeMillis(2000);
        mCircleProgressbar.reStart();

        mCircleProgressbar.setCountdownProgressListener(1, progressListener);

        mCircleProgressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleProgressbar.stop();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCircleProgressbar.start();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_splash;
    }


    @Override
    protected void onPause() {
        super.onPause();
        mCircleProgressbar.stop();
    }

    private CircleProgressbar.OnCountdownProgressListener progressListener = new CircleProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {

            if (what == 1 && progress == 100) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }
    };

}