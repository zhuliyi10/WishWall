package com.zhuliyi.app.wishwall;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zhuliyi.app.wishwall.config.Configuration;

/**
 * Created by zhuliyi on 2015/12/16.
 */
public class WishApllication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initEquipment();
        initImageLoader(getApplicationContext());
    }

    /**
     * imageLoader的初始化
     * @param context
     */
    public static void initImageLoader(Context context) {
        // This Configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default Configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with Configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public void initEquipment(){
        WindowManager wm=(WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Configuration.Equipment.SCREEN_WIDTH=wm.getDefaultDisplay().getWidth();
        Configuration.Equipment.SCREEN_HEIGHT=wm.getDefaultDisplay().getHeight();
    }

}
