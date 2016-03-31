package com.xiaoshan.googleplayv2.config;


import com.xiaoshan.googleplayv2.utils.LogUtils;

/**
 *
 */
public class Constants {

    public static final int DEBUG_LEVEL = LogUtils.LEVEL_ALL;//LEVEL_ALL,显示所有的日子,OFF:关闭日志的显示
    public static final int PAGE_SIZE = 20;
    public static final long VALIDATE_TIME = 5 * 60 * 1000;

    public static final class URLS {
        public static final String BASE_URL = "http://127.0.0.1:8090/";
        public static final String BASE_IMAGE_URL = BASE_URL + "image?name=";
        public static final String BASE_DOWNLOAD_URL = BASE_URL + "download";
    }

}
