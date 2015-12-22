package com.zhuliyi.app.wishwall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.activity.ImagePagerActivity;
import com.zhuliyi.app.wishwall.config.Constants;

/**
 * wish列表适配器
 * Created by zhuliyi on 2015/12/21.
 */
public class WishAdapter extends BaseAdapter {
    private ViewHolder holder;
    private static Context mContext;
    private String[] nameList = Constants.NAME_LSIT;
    private String[] contentList = Constants.CONTENT_LIST;

    public WishAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return nameList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_list_wish, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivHead.setImageResource(R.mipmap.default_avatar_female);
        holder.tvNickName.setText(nameList[position]);
        holder.tvSchool.setText("广州大学");
        holder.tvTime.setText("4小时前");
        holder.tvContent.setText(contentList[position]);

        holder.gvImage.setVisibility(View.VISIBLE);
        holder.gvImage.setAdapter(new ImageGridAdapter(mContext));
        refreshImageGridview();
        return convertView;
    }

    private void refreshImageGridview(){

    }
    /**
     * 列表item用到的viewHolder
     */
    private static class ViewHolder {
        public ImageView ivHead;
        public TextView tvNickName, tvSchool, tvTime, tvContent;
        public GridView gvImage;
        public LinearLayout llComment, llLike, llForward;

        public ViewHolder(View convertView) {
            ivHead = (ImageView) convertView.findViewById(R.id.imageView_head);
            tvNickName = (TextView) convertView.findViewById(R.id.textView_nickName);
            tvSchool = (TextView) convertView.findViewById(R.id.textView_school);
            tvTime = (TextView) convertView.findViewById(R.id.textView_time);
            tvContent = (TextView) convertView.findViewById(R.id.textView_content);
            llComment = (LinearLayout) convertView.findViewById(R.id.linearLayout_comment);
            llLike = (LinearLayout) convertView.findViewById(R.id.linearLayout_like);
            llForward = (LinearLayout) convertView.findViewById(R.id.linearLayout_forward);
            gvImage = (GridView) convertView.findViewById(R.id.gridView_image);

            gvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext, ImagePagerActivity.class);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
