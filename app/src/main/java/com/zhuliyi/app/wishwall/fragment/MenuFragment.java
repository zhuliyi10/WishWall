package com.zhuliyi.app.wishwall.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.activity.LoginActivity;
import com.zhuliyi.app.wishwall.util.CircleUtil;

/**
 * 侧滑面板，可以设置各种快捷设置
 * by zhuliyi
 * on 2015/11/29
 */
public class MenuFragment extends Fragment {

    private static MenuFragment fragment;
    private LinearLayout llLanded, llnLanded;
    private RelativeLayout rlInfo;
    private ImageView ivHead;
    private TextView tvNickName, tvIntroduce;
    private boolean isLanded;

    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance() {
        if (fragment == null) {
            fragment = new MenuFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        isLanded = false;
        initView(rootView);
        initData();
        return rootView;
    }

    /**
     * 初始化视图
     *
     * @param rootView
     */
    private void initView(View rootView) {
        llnLanded= (LinearLayout) rootView.findViewById(R.id.linearLayout_notLanded);

        llLanded = (LinearLayout) rootView.findViewById(R.id.linearLayout_landed);
        rlInfo = (RelativeLayout) rootView.findViewById(R.id.relativeLayout_info);
        ivHead = (ImageView) rootView.findViewById(R.id.imageView_head);
        tvNickName = (TextView) rootView.findViewById(R.id.textView_nickName);
        tvIntroduce = (TextView) rootView.findViewById(R.id.textView_introduction);


    }

    /**
     * 初始化数据
     */
    private void initData() {
        //更新视图
        if(isLanded) {
            llLanded.setVisibility(View.VISIBLE);
            llnLanded.setVisibility(View.GONE);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.head);
            ivHead.setImageBitmap(CircleUtil.toRoundBitmap(getContext(), bitmap));
            tvNickName.setText("风一样的流浪者");
            tvIntroduce.setText("生命在于运动，挑战不可能！");
        }else {
            llLanded.setVisibility(View.GONE);
            llnLanded.setVisibility(View.VISIBLE);
            llnLanded.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }


}
