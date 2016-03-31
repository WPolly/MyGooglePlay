package com.xiaoshan.googleplayv2.manager.bean;

import com.xiaoshan.googleplayv2.manager.DownloadManager;
import com.xiaoshan.googleplayv2.utils.FileUtils;

/**
 * Created by xiaoshan on 2016/3/3.
 * 10:57
 */
public class DownloadInfoBean {
    public String downloadUrl;
    public String savePath;
    public String packageName;
    public int currentDownloadState = DownloadManager.STATE_NOT_DOWNLOAD;
    public long progress;
    public long maxProgress;
    public Runnable downloadTask;

    public DownloadInfoBean(String downloadUrl, String savePath, String packageName) {
        this.downloadUrl = downloadUrl;
        this.savePath = savePath;
        this.packageName = packageName;
    }

    public DownloadInfoBean(String downloadUrl, String packageName) {
        this(downloadUrl, FileUtils.getDir("download"), packageName);
    }
}
