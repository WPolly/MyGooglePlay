package com.xiaoshan.googleplayv2.utils;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;
import com.xiaoshan.googleplayv2.config.Constants;

/**
 * Created by xiaoshan on 2016/2/21.
 * 16:17
 */
public class BitmapHelper {
    private static BitmapUtils bitmapUtils;

    public static <T extends View>  void display(T view , String iconUrl) {
        String url = Constants.URLS.BASE_IMAGE_URL + iconUrl;
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(UIUtils.getContext());
        }

        bitmapUtils.display(view,url);
    }
}
