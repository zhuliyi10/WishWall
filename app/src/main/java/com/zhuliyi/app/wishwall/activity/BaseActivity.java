package com.zhuliyi.app.wishwall.activity;
/**
 *  基本的类，包含自定义的actionBar，侧滑返回
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.view.MenuItem;

import java.lang.reflect.Field;


public class BaseActivity extends Activity implements SlidingPaneLayout.PanelSlideListener {
    private View viewLayout;
    public FrameLayout flMain;
    public RelativeLayout actionBar;
    public LinearLayout llLeft, llCenter, llRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_ani_enter, 0);
        viewLayout = View.inflate(this, R.layout.activity_base, null);
        actionBar = (RelativeLayout) viewLayout.findViewById(R.id.main_actionbar);
        llLeft = (LinearLayout) viewLayout.findViewById(R.id.linearLayout_left);
        llCenter = (LinearLayout) viewLayout.findViewById(R.id.linearLayout_center);
        llRight = (LinearLayout) viewLayout.findViewById(R.id.linearLayout_right);
        flMain = (FrameLayout) viewLayout.findViewById(R.id.frameLayout_main);

    }

    @Override
    public void setContentView(int viewId) {
        View view = View.inflate(this, viewId, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        flMain.addView(view);
        super.setContentView(viewLayout);
    }

    /**
     * 设置没有标题栏
     */
    public void setNoActionBar() {
        actionBar.setVisibility(View.GONE);
    }

    /**
     * 添加一个左边的item
     *
     * @param menuItem
     */
    public void addLeftItem(MenuItem menuItem) {
        llLeft.addView(menuItem.getView());
        llLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 移除左边所有item
     */
    public void clearLeftItem() {
        llLeft.removeAllViews();
        llLeft.setVisibility(View.GONE);
    }

    /**
     * 添加一个中间的item
     *
     * @param menuItem
     */
    public void addCenterItem(MenuItem menuItem) {
        llCenter.addView(menuItem.getView());
        llCenter.setVisibility(View.VISIBLE);
    }

    /**
     * 移除中间所有item
     */
    public void clearCenterItem() {
        llCenter.removeAllViews();
        llCenter.setVisibility(View.GONE);
    }

    /**
     * 添加一个右边的item
     *
     * @param menuItem
     */
    public void addRightItem(MenuItem menuItem) {
        llRight.addView(menuItem.getView());
        llRight.setVisibility(View.VISIBLE);
    }

    /**
     * 移除右边所有item
     */
    public void clearRightItem() {
        llRight.removeAllViews();
        llRight.setVisibility(View.GONE);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_ani_exist);
    }
    /**
     * 初始化滑动返回
     */
    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            SlidingPaneLayout slidingPaneLayout = new SlidingPaneLayout(this);
            //通过反射改变mOverhangSize的值为0，这个mOverhangSize值为菜单到右边屏幕的最短距离，默认
            //是32dp，现在给它改成0
            try {
                //属性
                Field f_overHang = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
                f_overHang.setAccessible(true);
                f_overHang.set(slidingPaneLayout, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            slidingPaneLayout.setPanelSlideListener(this);
            slidingPaneLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));

            View leftView = new View(this);
            leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            slidingPaneLayout.addView(leftView, 0);

            ViewGroup decor = (ViewGroup) getWindow().getDecorView();
            ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
            decorChild.setBackgroundColor(getResources().getColor(android.R.color.white));
            decor.removeView(decorChild);
            decor.addView(slidingPaneLayout);
            slidingPaneLayout.addView(decorChild, 1);
        }
    }
    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onPanelClosed(View view) {

    }

    @Override
    public void onPanelOpened(View view) {
        finish();
        this.overridePendingTransition(0, R.anim.activity_ani_exist);
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }
}
