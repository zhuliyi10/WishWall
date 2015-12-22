package com.zhuliyi.app.wishwall.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;
import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.fragment.ConfessionFragment;
import com.zhuliyi.app.wishwall.fragment.MenuFragment;
import com.zhuliyi.app.wishwall.fragment.MoodFragment;
import com.zhuliyi.app.wishwall.fragment.WishFragment;
import com.zhuliyi.app.wishwall.manager.ToastManager;
import com.zhuliyi.app.wishwall.view.MenuItem;

import java.util.ArrayList;

/**
 * 主activity,包含若干个fragment
 * by zhuliyi
 * on 2015/11/29
 */
public class MainActivity extends MainFragmentActivity {

    private static final int PAGECOUNT = 3;//主页面包含的子页面个数
    private int curentPage = 0;//目前的页面
    private int red1 = 73, green1 = 73, blue1 = 73;//字体的颜色变化
    private int red2 = 36, green2 = 170, blue2 = 254;
    private int redDif = red2 - red1, greenDif = green2 - green1, blueDif = blue2 - blue1;
    private ArrayList<Fragment> fragmentList;//fragment列表
    private ViewPager viewPager;//用于滑动的viewpager
    private MyPagerAdapter pagerAdapter;//用来装fragment的adapter
    private SlidingMenu menu;

    private LinearLayout[] ll_tab;
    private ImageView[] ivn_tab, iva_tab;
    private TextView[] tv_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ll_tab = new LinearLayout[]{
                (LinearLayout) findViewById(R.id.linearLayout1),
                (LinearLayout) findViewById(R.id.linearLayout2),
                (LinearLayout) findViewById(R.id.linearLayout3)};
        iva_tab = new ImageView[]{
                (ImageView) findViewById(R.id.imageView_1a),
                (ImageView) findViewById(R.id.imageView_2a),
                (ImageView) findViewById(R.id.imageView_3a)};
        ivn_tab = new ImageView[]{
                (ImageView) findViewById(R.id.imageView_1n),
                (ImageView) findViewById(R.id.imageView_2n),
                (ImageView) findViewById(R.id.imageView_3n)};
        tv_tab = new TextView[]{
                (TextView) findViewById(R.id.textView1),
                (TextView) findViewById(R.id.textView2),
                (TextView) findViewById(R.id.textView3)};

        //左边的MenuBar()
        addLeftItem(new MenuItem(this)
                        .show(MenuItem.ICON_LEFT, MenuItem.ITEM_ICON)
                        .setIcon(R.mipmap.menu_bar_sliding)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                menu.toggle();
                            }
                        })
        );
        //初始化菜单栏
        initSlidingMenu();

    }

    /**
     * SlidingMenu的设置
     */
    private void initSlidingMenu(){
        menu=new SlidingMenu(this);//初始化Slidingmenu
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);// 设置菜单滑动，触碰屏幕的范围
        menu.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 4);// 设置SlidingMenu边框距离
        menu.setBehindScrollScale(0f);//  设置SlidingMenu滑动的拖拽效果
        //menu.setFadeDegree(0.35f);// 设置SlidingMenu渐变
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//使SlidingMenu附加在Activity上
        menu.setMenu(R.layout.frame_menu);//设置menu的布局文件
        getSupportFragmentManager().beginTransaction()// 替换布局为 fragment
                .replace(R.id.frameLayout_menu, MenuFragment.newInstance()).commit();
    }
    /**
     * 初始化数据
     */
    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(WishFragment.newInstance());
        fragmentList.add(MoodFragment.newInstance());
        fragmentList.add(ConfessionFragment.newInstance());
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(PAGECOUNT);//缓存的页面数量

        setTabPosition(curentPage);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        for (int i = 0; i < ll_tab.length; i++) {
            ll_tab[i].setOnClickListener(tabListener);
            ll_tab[i].setTag(i);
        }
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 设置Tab的位置
     *
     * @param position 位置
     */
    private void setTabPosition(int position) {
        for (int i = 0; i < PAGECOUNT; i++) {
            if (i == position) {
                iva_tab[i].setAlpha(1f);
                ivn_tab[i].setAlpha(0f);
                tv_tab[i].setTextColor(Color.rgb(red2, green2, blue2));
            } else {
                iva_tab[i].setAlpha(0f);
                ivn_tab[i].setAlpha(1f);
                tv_tab[i].setTextColor(Color.rgb(red1, green1, blue1));
            }
        }
        curentPage = position;
        viewPager.setCurrentItem(position, false);
    }

    View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastManager.showToast(MainActivity.this,"你点击了我！", Toast.LENGTH_LONG);
        }
    };
    /**
     * 菜单栏的点击事件
     */
    View.OnClickListener tabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setTabPosition(Integer.valueOf(v.getTag().toString()));
        }
    };

    /**
     * ViewPager适配器
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //改变字体的颜色
            tv_tab[position].setTextColor(Color.rgb(
                    (int) (red2 - positionOffset * redDif),
                    (int) (green2 - positionOffset * greenDif),
                    (int) (blue2 - positionOffset * blueDif)));
            if (position != PAGECOUNT - 1) {
                tv_tab[position + 1].setTextColor(Color.rgb(
                        (int) (red1 + positionOffset * redDif),
                        (int) (green1 + positionOffset * greenDif),
                        (int) (blue1 + positionOffset * blueDif)));
            }

            //改变图片的透明度
            ivn_tab[position].setAlpha(positionOffset);
            iva_tab[position].setAlpha(1 - positionOffset);
            if (position != PAGECOUNT - 1) {
                iva_tab[position + 1].setAlpha(positionOffset);
                ivn_tab[position + 1].setAlpha(1 - positionOffset);
            }

        }

        @Override
        public void onPageSelected(int position) {
            setTabPosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
