package com.quaner.wxnews.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.quaner.wxnews.R;
import com.quaner.wxnews.mvp.ui.adapter.MyPagerAdapter;
import com.quaner.wxnews.mvp.ui.fragment.AndroidFragment;
import com.quaner.wxnews.mvp.ui.fragment.IosFragment;
import com.quaner.wxnews.mvp.ui.fragment.MeiZiFragment;
import com.wxandroid.common.CommonApplication;
import com.wxandroid.common.base.BaseActivity;
import com.wxandroid.common.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;


/**
 * Created by wenxin
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.coordinatortablayout)
    CoordinatorTabLayout coordinatortablayout;
    private int[] mImageArray;
    private int[] mColorArray;
    private List<Fragment> mFragments = new ArrayList();
    private final String[] mTitles = {"妹纸", "Android", "iOS"};
    private BaseDialog baseDialog;


    @Override
    protected void init() {
        initFragments();
        initViewPager();
        mImageArray = new int[]{
                R.mipmap.bg,
                R.mipmap.bg_android,
                R.mipmap.bg_other
        };
        mColorArray = new int[]{
                R.color.pink,
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light
        };

        coordinatortablayout.setTitle("wxNews")
                .setBackEnable(true)
                .setImageArray(mImageArray, mColorArray)
                .setupWithViewPager(vp);
    }

    @Override
    protected void initInject() {

    }

    private void initViewPager() {
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), (ArrayList<Fragment>) mFragments, mTitles));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        switch (item.getItemId()) {
            case R.id.about_me:
                startActivity(new Intent(MainActivity.this, MeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void initFragments() {
        mFragments.add(new MeiZiFragment());
        mFragments.add(new AndroidFragment());
        mFragments.add(new IosFragment());
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        baseDialog = new BaseDialog(this)
                .setTitle("提示")
                .setMessage("您确定要退出应用吗？")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseDialog.dismiss();
                    }
                }).setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseDialog.dismiss();
                        CommonApplication.exit();
                        finish();
                    }
                });
    }
}
