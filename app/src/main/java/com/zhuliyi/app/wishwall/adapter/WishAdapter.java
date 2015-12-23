package com.zhuliyi.app.wishwall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.activity.ImagePagerActivity;
import com.zhuliyi.app.wishwall.config.Constants;
import com.zhuliyi.app.wishwall.manager.ToastManager;
import com.zhuliyi.app.wishwall.util.CircleUtil;
import com.zhuliyi.app.wishwall.view.CircleDisplayer;

/**
 * wish列表适配器
 * Created by zhuliyi on 2015/12/21.
 */
public class WishAdapter extends BaseAdapter {
    private ViewHolder holder;
    private static Context mContext;
    private DisplayImageOptions options;
    private String[] nameList = Constants.NAME_LSIT;
    private String[] contentList = Constants.CONTENT_LIST;

    public WishAdapter(Context context) {
        this.mContext = context;
        options=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_stub)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new CircleDisplayer())//实现圆形化
                .build();
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
        //        holder.ivHead.setImageResource(R.mipmap.default_avatar_female);
        Bitmap bitmap= BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.default_avatar_female);
        holder.ivHead.setImageBitmap(CircleUtil.toRoundBitmap(mContext, bitmap));

        String headUrl="http://f.hiphotos.baidu.com/image/pic/item/91ef76c6a7efce1b20413118ab51f3deb58f65fe.jpg";
        ImageLoader.getInstance().displayImage(headUrl,holder.ivHead,options);

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
        public LinearLayout llItem;
        public ImageView ivHead;
        public TextView tvNickName, tvSchool, tvTime, tvContent;
        public GridView gvImage;
        public LinearLayout llComment, llLike, llForward;

        public ViewHolder(View convertView) {
            llItem= (LinearLayout) convertView.findViewById(R.id.linearLayout_item);
            ivHead = (ImageView) convertView.findViewById(R.id.imageView_head);
            tvNickName = (TextView) convertView.findViewById(R.id.textView_nickName);
            tvSchool = (TextView) convertView.findViewById(R.id.textView_school);
            tvTime = (TextView) convertView.findViewById(R.id.textView_time);
            tvContent = (TextView) convertView.findViewById(R.id.textView_content);
            llComment = (LinearLayout) convertView.findViewById(R.id.linearLayout_comment);
            llLike = (LinearLayout) convertView.findViewById(R.id.linearLayout_like);
            llForward = (LinearLayout) convertView.findViewById(R.id.linearLayout_forward);
            gvImage = (GridView) convertView.findViewById(R.id.gridView_image);

            llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastManager.showToast(mContext,"这是第listview的item", Toast.LENGTH_LONG);
                }
            });
            gvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext, ImagePagerActivity.class);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
                }
            });


            ivHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastManager.showToast(mContext,"你点击了头像", Toast.LENGTH_LONG);
                }
            });

        }
    }
}
