package com.zhuliyi.app.wishwall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.view.MenuItem;

/**
 * 用户注册
 * by zhuliyi on 2015/12/26
 */
public class RegisterActivity extends BaseActivity {

    private LinearLayout llAcount,llPhone;
    private TextView tvUnderline1,tvUnderline2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListener();

    }

    /**
     * 初始化视图
     */
    private void initView(){
        addLeftItem(new MenuItem(this).
                        show(MenuItem.ICON_LEFT, MenuItem.ITEM_ICON).
                        setIcon(R.mipmap.left_arrow_write).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
        );
        addCenterItem(new MenuItem(this).show(MenuItem.ICON_LEFT, MenuItem.ITEM_TITLE).setTitle("注册"));

        llAcount= (LinearLayout) findViewById(R.id.linearLayout_account);
        llPhone= (LinearLayout) findViewById(R.id.linearLayout_phone);
        tvUnderline1= (TextView) findViewById(R.id.textView_underline1);
        tvUnderline2= (TextView) findViewById(R.id.textView_underline2);
    }

    /**
     * 设置监听器
     */
    private void setListener(){
        llAcount.setOnClickListener(listener);
        llPhone.setOnClickListener(listener);
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.linearLayout_account:
                    tvUnderline1.setVisibility(View.VISIBLE);
                    tvUnderline2.setVisibility(View.INVISIBLE);
                    break;
                case R.id.linearLayout_phone:
                    tvUnderline1.setVisibility(View.INVISIBLE);
                    tvUnderline2.setVisibility(View.VISIBLE);
                default:break;
            }
        }
    };
}
