package com.xiaoshan.googleplayv2.bean;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/27.
 * 16:33
 */
public class DetailBean {

    /**
     * author : 北京千橡网景科技发展有限公司
     * date : 2014-05-20
     * des : 2005-2014 你的校园一直在这儿。
     * downloadNum : 1000万+
     * downloadUrl : app/com.renren.mobile.android/com.renren.mobile.android.apk
     * iconUrl : app/com.renren.mobile.android/icon.jpg
     * id : 1580615
     * name : 人人
     * packageName : com.renren.mobile.android
     * safe : [{"safeDes":"已通过安智市场官方认证，是正版软件","safeDesColor":0,"safeDesUrl":"app/com.renren.mobile.android/safeDesUrl0.jpg","safeUrl":"app/com.renren.mobile.android/safeIcon0.jpg"}]
     * screen : ["app/com.renren.mobile.android/screen0.jpg","app/com.renren.mobile.android/screen1.jpg","app/com.renren.mobile.android/screen2.jpg","app/com.renren.mobile.android/screen3.jpg","app/com.renren.mobile.android/screen4.jpg"]
     * size : 21803987
     * stars : 2.0
     * version : 7.5.3
     */

    public String author;
    public String date;
    public String des;
    public String downloadNum;
    public String downloadUrl;
    public String iconUrl;
    public int id;
    public String name;
    public String packageName;
    public long size;
    public float stars;
    public String version;
    /**
     * safeDes : 已通过安智市场官方认证，是正版软件
     * safeDesColor : 0
     * safeDesUrl : app/com.renren.mobile.android/safeDesUrl0.jpg
     * safeUrl : app/com.renren.mobile.android/safeIcon0.jpg
     */

    public List<SafeBean> safe;
    public List<String> screen;

    public static class SafeBean {
        public String safeDes;
        public int safeDesColor;
        public String safeDesUrl;
        public String safeUrl;
    }
}
