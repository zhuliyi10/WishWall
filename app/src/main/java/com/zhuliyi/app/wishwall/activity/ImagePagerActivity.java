package com.zhuliyi.app.wishwall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.adapter.ViewPagerAdapter;

/**
 * Created by zhuliyi on 2015/12/21.
 */
public class ImagePagerActivity extends Activity {
    private ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        int position=getIntent().getIntExtra("position",0);
        pager= (ViewPager) findViewById(R.id.viewPager_image);

        pager.setAdapter(new ViewPagerAdapter(this));
        pager.setCurrentItem(position);

    }
}
