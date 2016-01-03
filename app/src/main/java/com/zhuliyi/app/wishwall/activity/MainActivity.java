package com.zhuliyi.app.wishwall.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.config.Configuration;
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
    private int red1 = 128, green1 = 128, blue1 = 128;//字体的颜色变化
    private int red2 = 36, green2 = 170, blue2 = 254;
    private int redDif = red2 - red1, greenDif = green2 - green1, blueDif = blue2 - blue1;
    private ArrayList<Fragment> fragmentList;//fragment列表
    private ViewPager viewPager;//用于滑动的viewpager
    private MyPagerAdapter pagerAdapter;//用来装fragment的adapter
    private MenuItem menuCenter;

    private DrawerLayout drawerLayout;//抽屉布局
    private FrameLayout flMenu;//侧滑菜单
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
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        flMenu = (FrameLayout) findViewById(R.id.frame_menu);
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
                                drawerLayout.openDrawer(GravityCompat.START);//打开抽屉
                            }
                        })
        );


    }

    /**
     * 初始化抽屉菜单
     */
    private void initDrawerMenu() {
        getSupportFragmentManager().beginTransaction()// 替换布局为 fragment
                .replace(R.id.frame_menu, MenuFragment.newInstance()).commit();
        //自定义menu宽度的大小
        ViewGroup.LayoutParams lp=flMenu.getLayoutParams();
        lp.width= Configuration.Equipment.SCREEN_WIDTH/6*5;
        flMenu.setLayoutParams(lp);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        initDrawerMenu();//初始化抽屉菜单

        initTabFontColor();
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
        drawerLayout.setDrawerListener(new MyDrawerListener());//设置抽屉状态变化的监听
        for (int i = 0; i < ll_tab.length; i++) {
            ll_tab[i].setOnClickListener(tabListener);
            ll_tab[i].setTag(i);
        }
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 初始化tab字体颜色
     */
    private void initTabFontColor() {
        int deactivation = getResources().getColor(R.color.font_unfocus);
        int activation = getResources().getColor(R.color.font_style);
        red1 = Color.red(deactivation);
        green1 = Color.green(deactivation);
        blue1 = Color.blue(deactivation);
        red2 = Color.red(activation);
        green2 = Color.green(activation);
        blue2 = Color.blue(activation);
        redDif = red2 - red1;
        greenDif = green2 - green1;
        blueDif = blue2 - blue1;
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
        //设定标题
        String titile;
        switch (curentPage) {
            case 0:
                titile = getResources().getString(R.string.wish);
                break;
            case 1:
                titile = getResources().getString(R.string.mood);
                break;
            case 2:
                titile = getResources().getString(R.string.confession);
                break;
            default:
                titile = getResources().getString(R.string.wish);
                break;
        }
        if (menuCenter == null) {
            menuCenter = new MenuItem(this);
            addCenterItem(menuCenter.show(MenuItem.ICON_LEFT, MenuItem.ITEM_TITLE));
        }
        menuCenter.setTitle(titile);

    }

    View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastManager.showToast(MainActivity.this, "你点击了我！", Toast.LENGTH_LONG);
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

    /**
     * DrawerLayout状态变化监听
     */
    private class MyDrawerListener extends DrawerLayout.SimpleDrawerListener {
        public MyDrawerListener() {
            super();
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
        }
    }

}
