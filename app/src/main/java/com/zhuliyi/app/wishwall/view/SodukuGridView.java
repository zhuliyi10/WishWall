package com.zhuliyi.app.wishwall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义的“九宫格”——用在显示帖子详情的图片集合
 * 解决的问题：GridView显示不全，只显示了一行的图片，比较奇怪，尝试重写GridView来解决
 * Created by zhuliyi on 2015/12/21.
 */
public class SodukuGridView extends GridView{
    public SodukuGridView(Context context) {
        super(context);
    }

    public SodukuGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SodukuGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int  size = getAdapter().getCount();
        int expandSpec=0;
        if(size==1){
            setNumColumns(1);
        }else if(size==2||size==4){
            setNumColumns(2);
        }else {
            setNumColumns(3);
        }
        expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
