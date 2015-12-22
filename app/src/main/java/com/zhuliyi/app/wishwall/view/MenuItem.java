package com.zhuliyi.app.wishwall.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuliyi.app.wishwall.R;


/**
 * 设置图标文字的显示和相对位置
 * Created by zhuliyi on 15-10-18.
 */
public class MenuItem {
    public static final int ITEM_TITLE = 1;
    public static final int ITEM_ICON = 2;
    public static final int ITEM_BOTH = 3;
    public static final int ICON_LEFT = 1;//图标在左
    public static final int ICON_BOTTOM = 2;
    public static final int ICON_RIGHT = 3;
    public static final int ICON_TOP = 4;

    private View result;
    private ImageView ivIcon;
    private TextView tvTitle;
    public Context context;

    public MenuItem() {
    }

    public MenuItem(Context context) {
        this.context = context;

    }


    /**
     * 设置图片位置
     * 设置只显示按钮、文字或两者都显示
     *@param iconPosition
     * @param mode ITEM_TITLE，ITEM_ICON，ITEM_BOTH
     * @return
     */
    public MenuItem show(int iconPosition,int mode) {
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (iconPosition) {
            case ICON_LEFT:
                result = inflater.inflate(R.layout.item_icon_left, null);
                break;
            case ICON_BOTTOM:
                result = inflater.inflate(R.layout.item_icon_right, null);
                break;
            case ICON_RIGHT:
                result = inflater.inflate(R.layout.item_icon_right, null);
                break;
            case ICON_TOP:
                result = inflater.inflate(R.layout.item_icon_right, null);
                break;
            default:
                result = inflater.inflate(R.layout.item_icon_right, null);
                break;
        }
        ivIcon = (ImageView) result.findViewById(R.id.imageView_icon);
        tvTitle = (TextView) result.findViewById(R.id.testView_title);
        switch (mode) {
            case ITEM_TITLE:
                ivIcon.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                break;
            case ITEM_ICON:
                ivIcon.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.GONE);
                break;
            case ITEM_BOTH:
                ivIcon.setPadding(ivIcon.getPaddingLeft(), ivIcon.getPaddingTop(), ivIcon.getPaddingRight() / 2, ivIcon.getPaddingBottom());
                tvTitle.setPadding(tvTitle.getPaddingLeft() / 2, tvTitle.getPaddingTop(), tvTitle.getPaddingRight(), tvTitle.getPaddingBottom());
                ivIcon.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                break;
            default:
                result.setVisibility(View.GONE);
                break;
        }
        return this;
    }

    /**
     * 设置文字
     *
     * @param title
     * @return
     */
    public MenuItem setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    /**
     * 设置图标
     *
     * @param icon
     * @return
     */
    public MenuItem setIcon(int icon) {
        ivIcon.setImageResource(icon);
        return this;
    }

    /**
     * 设置动作监听器
     *
     * @param listener
     * @return
     */
    public MenuItem setOnClickListener(View.OnClickListener listener) {
        result.setOnClickListener(listener);
        return this;
    }

    public MenuItem setView(View view) {
        result = view;
        return this;
    }

    public View getView() {
        return result;
    }

    public ImageView getIcon() {
        return ivIcon;
    }
}
