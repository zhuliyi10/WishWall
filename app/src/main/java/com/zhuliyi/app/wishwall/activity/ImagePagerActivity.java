package com.zhuliyi.app.wishwall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.adapter.ImageGridAdapter;
import com.zhuliyi.app.wishwall.fragment.ImageDetailFragment;

/**
 * 用于图片的滑动
 * 可以两种方法实现adapter,一种是viewpageradapter,用布局来实现，另一种是FragmentStatePagerAdapter用fragment来实现
 * Created by zhuliyi on 2015/12/21.
 */
public class ImagePagerActivity extends FragmentActivity {

    private ViewPager pager;
    private ViewGroup group;
    private ImageView[] ivDots = null;
    private String[] imagesUrl= ImageGridAdapter.imagesUrl;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_pager);
        position=getIntent().getIntExtra("position",0);
        pager= (ViewPager) findViewById(R.id.viewPager_image);
        group= (ViewGroup)findViewById(R.id.linearLayout_group);
//        pager.setAdapter(new ViewPagerAdapter(this));
        pager.setAdapter(new MyFragmentStatePagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(position);
        pager.setOnPageChangeListener(new MyPageChangeListener());
        initDots();


    }


    /**
     * 初始化下标的点
     */
    private void initDots(){
        ivDots = new ImageView[imagesUrl.length];
        for (int i = 0; i < imagesUrl.length; i++) {
            ImageView ivDot = new ImageView(this);
            ivDot.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            ivDot.setPadding(5, 5, 5, 5);
            ivDots[i] = ivDot;
            if (i == position) {
                ivDots[i]
                        .setBackgroundResource(R.mipmap.banner_dian_focus);
            } else {
                ivDots[i]
                        .setBackgroundResource(R.mipmap.banner_dian_blur);
            }
            group.addView(ivDots[i]);
        }
    }

    /**
     * fragment的适配器
     */
    private class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter{

        public MyFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return  ImageDetailFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return imagesUrl.length;
        }
    }
    /**
     * viewPager切换变化监听器
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < ivDots.length; i++) {
                if (i == position) {
                    ivDots[i]
                            .setBackgroundResource(R.mipmap.banner_dian_focus);
                } else {
                    ivDots[i]
                            .setBackgroundResource(R.mipmap.banner_dian_blur);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
