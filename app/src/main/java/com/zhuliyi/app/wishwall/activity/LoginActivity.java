package com.zhuliyi.app.wishwall.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.manager.ToastManager;
import com.zhuliyi.app.wishwall.view.MenuItem;

/**
 * 登陆界面
 * by zhuliyi on 2015/12/25
 */
public class LoginActivity extends BaseActivity {

    private EditText etAccount,etPwd;
    private TextView tvRegister,tvForget;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        addCenterItem(new MenuItem(this).show(MenuItem.ICON_LEFT, MenuItem.ITEM_TITLE).setTitle("登陆"));

        etAccount= (EditText) findViewById(R.id.editText_account);
        etPwd= (EditText) findViewById(R.id.editText_pwd);
        btLogin= (Button) findViewById(R.id.button_login);
        tvRegister= (TextView) findViewById(R.id.textView_register);
        tvForget= (TextView) findViewById(R.id.textView_forget);

        //设置editText里的图片
        Drawable dAccount=getResources().getDrawable(R.mipmap.login_edit_account);
        dAccount.setBounds(0,0,dAccount.getMinimumWidth(),dAccount.getMinimumHeight());
        etAccount.setCompoundDrawables(dAccount, null, null, null);//必须设置图片大小，否则不显示
        Drawable dPwd=getResources().getDrawable(R.mipmap.login_edit_password);
        dPwd.setBounds(0, 0, dPwd.getMinimumWidth(), dPwd.getMinimumHeight());
        etPwd.setCompoundDrawables(dPwd, null, null, null);


    }

    /**
     * 设置监听器
     */
    private void setListener(){
        btLogin.setOnClickListener(listener);
        tvRegister.setOnClickListener(listener);
    }

    /**
     * 监听处理
     */
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.button_login:
                    String acount=etAccount.getText().toString().trim();
                    String pwd=etPwd.getText().toString().trim();
                    if(acount.equals("")){
                        ToastManager.showToast(LoginActivity.this,"请输入用户名-_-", Toast.LENGTH_LONG);
                    }else if(pwd.equals("")){
                        ToastManager.showToast(LoginActivity.this,"请输入密码-_-", Toast.LENGTH_LONG);
                    }

                    break;
                case R.id.textView_register:
                    Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    break;
                default:break;
            }
        }
    };
}
