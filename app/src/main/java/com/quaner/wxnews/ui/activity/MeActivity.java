package com.quaner.wxnews.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.quaner.wxnews.R;
import com.quaner.wxnews.common.AppComponent;
import com.quaner.wxnews.common.WXActivity;

import butterknife.OnClick;

/**
 * Created by wenxin
 */
public class MeActivity extends WXActivity {

    Uri uri = null;
    Intent intent = null;


    @OnClick({R.id.tv_MVPArms, R.id.tv_wxNews})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_MVPArms:
                uri = Uri.parse("https://github.com/JessYanCoding/MVPArms");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                break;
            case R.id.tv_wxNews:
                uri = Uri.parse("https://github.com/yangwenxin/wxNews");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                break;
        }
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_me;
    }

}
