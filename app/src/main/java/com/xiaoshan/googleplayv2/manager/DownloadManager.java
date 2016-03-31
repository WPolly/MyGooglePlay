package com.xiaoshan.googleplayv2.manager;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiaoshan.googleplayv2.bean.AppInfoBean;
import com.xiaoshan.googleplayv2.bean.DetailBean;
import com.xiaoshan.googleplayv2.config.Constants;
import com.xiaoshan.googleplayv2.factory.ThreadPoolFactory;
import com.xiaoshan.googleplayv2.manager.bean.DownloadInfoBean;
import com.xiaoshan.googleplayv2.utils.ApkUtils;
import com.xiaoshan.googleplayv2.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by xiaoshan on 2016/3/2.
 * 12:59
 */
public class DownloadManager {
    public static final int STATE_NOT_DOWNLOAD = 0;
    public static final int STATE_WAIT_DOWNLOAD =   1;
    public static final int STATE_DOWNLOADING = 2;
    public static final int STATE_PAUSE_DOWNLOAD = 3;
    public static final int STATE_DOWNLOAD_SUCCESS = 4;
    public static final int STATE_DOWNLOAD_FAILED = 5;
    public static final int STATE_INSTALLED = 6;

    private static DownloadManager instance = null;
    private Map<String, DownloadInfoBean> mDownloadInfoMap = new HashMap<>();
    private ThreadPoolExecutor mThreadPoolExecutor;

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public void download(DownloadInfoBean downloadInfo) {
        mDownloadInfoMap.put(downloadInfo.packageName, downloadInfo);
        downloadInfo.currentDownloadState = STATE_WAIT_DOWNLOAD;
        notifyObservers(downloadInfo);
        DownloadTask downloadTask = new DownloadTask(downloadInfo);
        downloadInfo.downloadTask = downloadTask;
        mThreadPoolExecutor = ThreadPoolFactory.getDownLoadThreadPool().getThreadPoolExecutor();
        mThreadPoolExecutor.execute(downloadTask);
    }

    public void cancelDownload(DownloadInfoBean downloadInfo) {
        mThreadPoolExecutor.remove(downloadInfo.downloadTask);
        downloadInfo.currentDownloadState = STATE_NOT_DOWNLOAD;
        notifyObservers(downloadInfo);
    }

    public void pauseDownload(DownloadInfoBean downloadInfo) {
        downloadInfo.currentDownloadState = STATE_PAUSE_DOWNLOAD;
        notifyObservers(downloadInfo);
    }

    public void installApk(DownloadInfoBean downloadInfo) {
        File file = new File(downloadInfo.savePath, downloadInfo.packageName+".apk");
        ApkUtils.installApp(UIUtils.getContext(), file);
    }

    public void openApp(DownloadInfoBean downloadInfo) {
        ApkUtils.openApp(UIUtils.getContext(), downloadInfo.packageName);
    }



    class DownloadTask implements Runnable {
        private DownloadInfoBean mDownloadInfo;

        public DownloadTask(DownloadInfoBean downloadInfo) {
            mDownloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            HttpUtils httpUtils = new HttpUtils();
            RequestParams params = new RequestParams();
            long range = 0;
            File apkFile = new File(mDownloadInfo.savePath,mDownloadInfo.packageName+".apk");
            if (apkFile.exists()) {
                range = apkFile.length();
            }
            params.addQueryStringParameter("name", mDownloadInfo.downloadUrl);
            params.addQueryStringParameter("range", range+"");
            try {
                ResponseStream responseStream =
                        httpUtils.sendSync(HttpRequest.HttpMethod.GET, Constants.URLS.BASE_DOWNLOAD_URL, params);
                int statusCode = responseStream.getStatusCode();
                if (statusCode == 200) {
                    InputStream inputStream = responseStream.getBaseStream();
                    File file = new File(mDownloadInfo.savePath, mDownloadInfo.packageName + ".apk");
                    OutputStream outputStream = new FileOutputStream(file,true);//true: 追加写入
                    byte[] buffer = new byte[2048];
                    int len;
                    long progress = range;
                    while ((len = inputStream.read(buffer)) != -1) {
                        if (mDownloadInfo.currentDownloadState == STATE_PAUSE_DOWNLOAD) {
                            break;
                        }
                        mDownloadInfo.currentDownloadState = STATE_DOWNLOADING;
                        outputStream.write(buffer, 0, len);
                        progress += len;
                        mDownloadInfo.progress = progress;
                        notifyObservers(mDownloadInfo);
                    }
                    inputStream.close();
                    outputStream.close();
                    if (mDownloadInfo.currentDownloadState != STATE_PAUSE_DOWNLOAD) {
                        mDownloadInfo.currentDownloadState = STATE_DOWNLOAD_SUCCESS;
                        notifyObservers(mDownloadInfo);
                    }
                } else {
                    mDownloadInfo.currentDownloadState = STATE_DOWNLOAD_FAILED;
                    notifyObservers(mDownloadInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mDownloadInfo.currentDownloadState = STATE_DOWNLOAD_FAILED;
                notifyObservers(mDownloadInfo);
            }
        }
    }

    /**
     * 供ViewHolder得到特定的下载相关数据
     * @param data 详情页的数据bean.
     * @return 特定的下载相关数据
     */
    public DownloadInfoBean getDownloadInfo(DetailBean data) {
        DownloadInfoBean downloadInfo = new DownloadInfoBean(data.downloadUrl, data.packageName);
        downloadInfo.maxProgress = data.size;

        if (ApkUtils.isInstalled(UIUtils.getContext(),data.packageName)) {
            downloadInfo.currentDownloadState = STATE_INSTALLED;
            return downloadInfo;
        }

        File file = new File(downloadInfo.savePath, downloadInfo.packageName+".apk");
        if (file.exists() && file.length() == data.size) {
            downloadInfo.currentDownloadState = STATE_DOWNLOAD_SUCCESS;
            return downloadInfo;
        }

        DownloadInfoBean infoBean = mDownloadInfoMap.get(data.packageName);
        if (infoBean != null) {
            downloadInfo = infoBean;
        }

        return downloadInfo;
    }

    public DownloadInfoBean getDownloadInfo(AppInfoBean data) {
        DownloadInfoBean downloadInfo = new DownloadInfoBean(data.downloadUrl, data.packageName);
        downloadInfo.maxProgress = data.size;

        if (ApkUtils.isInstalled(UIUtils.getContext(),data.packageName)) {
            downloadInfo.currentDownloadState = STATE_INSTALLED;
            return downloadInfo;
        }

        File file = new File(downloadInfo.savePath, downloadInfo.packageName+".apk");
        if (file.exists() && file.length() == data.size) {
            downloadInfo.currentDownloadState = STATE_DOWNLOAD_SUCCESS;
            return downloadInfo;
        }

        DownloadInfoBean infoBean = mDownloadInfoMap.get(data.packageName);
        if (infoBean != null) {
            downloadInfo = infoBean;
        }

        return downloadInfo;
    }

    //region 自定义观察者 begin
    public interface DownLoadObserver {
        void onDownloadInfoChanged(DownloadInfoBean info);
    }

    public List<DownLoadObserver> getObservers() {
        return mObservers;
    }

    private List<DownLoadObserver> mObservers = new LinkedList<>();

    public void addObserver(DownLoadObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer can't be null");
        } else {
            if (!mObservers.contains(observer)) {
                synchronized (this) {
                    mObservers.add(observer);
                }
            }
        }
    }

    public synchronized void deleteObserver(DownLoadObserver observer) {
        mObservers.remove(observer);
    }

    public void notifyObservers(DownloadInfoBean info) {
        for (DownLoadObserver observer: mObservers) {
            observer.onDownloadInfoChanged(info);
        }

    }
    //region 自定义观察者 end
}
