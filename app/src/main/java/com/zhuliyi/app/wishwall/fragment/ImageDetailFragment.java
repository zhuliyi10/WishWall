package com.zhuliyi.app.wishwall.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
import com.zhuliyi.app.wishwall.adapter.ImageGridAdapter;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 用于图片的滑动，用fragment来实现，
 * A simple {@link Fragment} subclass.
 */
public class ImageDetailFragment extends Fragment {

    private PhotoViewAttacher mAttacher;
    private DisplayImageOptions options;
    private String[] imagesUrl= ImageGridAdapter.imagesUrl;
    private int position;

    public static ImageDetailFragment newInstance(int pos){
        final ImageDetailFragment  fragment=new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putInt("position", pos);
        fragment.setArguments(args);
        return fragment;
    }
    public ImageDetailFragment(){
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取上一个状态保存的位置，要不然图片显示会错乱
        position = getArguments() != null ? getArguments().getInt("position") : null;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.item_pager_image, container, false);
        ImageView imageView= (ImageView) rootView.findViewById(R.id.photoView_pager);
        final ProgressBar progressBar= (ProgressBar) rootView.findViewById(R.id.progressBar_pager);
        mAttacher=new PhotoViewAttacher(imageView);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                getActivity().finish();
            }
        });
        //imageLoader显示网络图片
        ImageLoader.getInstance().displayImage(imagesUrl[position],imageView,options,new SimpleImageLoadingListener(){
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
                mAttacher.update();//这个一定要加上，要不然图片会突变，这个问题搞了我几天
            }
        });

        return rootView;
    }


}
