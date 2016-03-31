package com.xiaoshan.googleplayv2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xiaoshan.googleplayv2.holder.AppItemHolder;
import com.xiaoshan.googleplayv2.manager.DownloadManager;
import com.xiaoshan.googleplayv2.manager.bean.DownloadInfoBean;

import java.util.List;

/**
 * Created by xiaoshan on 2016/3/6.
 * 14:21
 */
public class ApkInstallStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getSchemeSpecificPart();
        DownloadManager instance = DownloadManager.getInstance();
        List<DownloadManager.DownLoadObserver> observers = instance.getObservers();
        for (DownloadManager.DownLoadObserver observer : observers) {
            DownloadInfoBean downloadInfo = ((AppItemHolder) observer).getDownloadInfo();
            if (downloadInfo.packageName.equals(packageName)) {
                if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                    downloadInfo.currentDownloadState = DownloadManager.STATE_INSTALLED;
                    instance.notifyObservers(downloadInfo);
                }
            }
        }
    }
}
