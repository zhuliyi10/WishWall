package com.zhuliyi.app.wishwall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.config.Configuration;
import com.zhuliyi.app.wishwall.config.Constants;

/**
 * gridView适配器
 * Created by zhuliyi on 2015/12/21.
 */
public class ImageGridAdapter extends BaseAdapter{
    private Context mContext;
    private DisplayImageOptions options;
    public static String[] imagesUrl= Constants.IMAGES_URL;

    public ImageGridAdapter(Context context){
        mContext=context;
        options=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_stub)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }
    @Override
    public int getCount() {
        return imagesUrl.length;
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
        final ViewHolder holder;
        if(convertView==null){
            convertView=View.inflate(mContext, R.layout.item_grid_image,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        //改变图片显示的大小
        ViewGroup.LayoutParams lp=holder.imageView.getLayoutParams();
        if(imagesUrl.length==1){
            lp.width=(Configuration.Equipment.SCREEN_WIDTH-20);
            lp.height=(Configuration.Equipment.SCREEN_WIDTH-20);
        }else if(imagesUrl.length==2||imagesUrl.length==4){
            lp.width=(Configuration.Equipment.SCREEN_WIDTH-30)/2;
            lp.height=(Configuration.Equipment.SCREEN_WIDTH-30)/2;
        }else {
            lp.width=(Configuration.Equipment.SCREEN_WIDTH-40)/3;
            lp.height=(Configuration.Equipment.SCREEN_WIDTH-40)/3;
        }
        holder.imageView.setLayoutParams(lp);

//        if(imagesUrl.length==1){
//            holder.imageView.setLayoutParams(new ViewGroup.LayoutParams(Configuration.Equipment.SCREEN_WIDTH-20,Configuration.Equipment.SCREEN_WIDTH-20));
//        }else if(imagesUrl.length==2||imagesUrl.length==4){
//            holder.imageView.setLayoutParams(new ViewGroup.LayoutParams((Configuration.Equipment.SCREEN_WIDTH-40)/2,(Configuration.Equipment.SCREEN_WIDTH-40)/2));
//        }else {
//            holder.imageView.setLayoutParams(new ViewGroup.LayoutParams(120,120));
//        }
        //加载网络图片
        ImageLoader.getInstance()
                .displayImage(imagesUrl[position], holder.imageView, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        holder.progressBar.setProgress(0);
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        holder.progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String s, View view, int current, int total) {
                        holder.progressBar.setProgress(Math.round(100.0f*current/total ));
                    }
                });
        return convertView;
    }
    /**
     * gridView item用到的viewHolder
     */
    private static class ViewHolder{
        public ImageView imageView;
        public ProgressBar progressBar;
        public ViewHolder(View convertView){
            imageView= (ImageView) convertView.findViewById(R.id.imageView_item);
            progressBar= (ProgressBar) convertView.findViewById(R.id.progressBar_item);

        }
    }
}
