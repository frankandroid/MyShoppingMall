package com.example.frank.myshoppingmall.http;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.frank.myshoppingmall.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * 创建者     Frank
 * 创建时间   2016/4/23 9:35
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class ImageLoaderUtil {

    /**
     * 配置加载图片的一些参数
     */
    static DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)
            .showImageForEmptyUri(R.mipmap.ic_launcher)
            .showImageOnFail(R.mipmap.ic_launcher)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .build();


    public static void displayImage(String url, ImageView imageView) {

        ImageLoader.getInstance().displayImage(url, imageView, mOptions);

    }


}
