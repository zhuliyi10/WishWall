package com.zhuliyi.app.wishwall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zhuliyi.app.wishwall.R;

import uk.co.senab.photoview.PhotoViewAttacher;

import static com.zhuliyi.app.wishwall.R.id.photoView_pager;
import static com.zhuliyi.app.wishwall.R.id.progressBar_item;

/**
 * Created by zhuliyi on 2015/12/21.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private PhotoViewAttacher attacher;
    private DisplayImageOptions options;
    private Context mContext;
    private String[] imagesUrl=ImageGridAdapter.imagesUrl;

    public ViewPagerAdapter(Context context){
        this.mContext=context;
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片的伸缩方式
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View rootView=View.inflate(mContext, R.layout.item_pager_image,null);
        ImageView imageView= (ImageView) rootView.findViewById(photoView_pager);
        final ProgressBar progressBar= (ProgressBar) rootView.findViewById(progressBar_item);
        attacher=new PhotoViewAttacher(imageView);

        ImageLoader.getInstance().displayImage(imagesUrl[position],imageView, options,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }
        });
        container.addView(rootView,0);
        return rootView;
    }

    @Override
    public int getCount() {
        return imagesUrl.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


}
