package com.xiaoshan.googleplayv2.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.DetailBean;
import com.xiaoshan.googleplayv2.manager.DownloadManager;
import com.xiaoshan.googleplayv2.manager.bean.DownloadInfoBean;
import com.xiaoshan.googleplayv2.utils.UIUtils;
import com.xiaoshan.googleplayv2.view.ProgressButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaoshan on 2016/3/2.
 * 9:03
 */
public class AppDetailDownloadHolder extends BaseViewHolder<DetailBean> implements View.OnClickListener, DownloadManager.DownLoadObserver {
    @InjectView(R.id.app_detail_download_btn_favo)
    Button mAppDetailBtnFavour;
    @InjectView(R.id.app_detail_download_btn_share)
    Button mAppDetailBtnShare;
    @InjectView(R.id.app_detail_download_btn_download)
    ProgressButton mAppDetailBtnDownload;

    private DownloadManager mDownloadManager;
    private DownloadInfoBean mDownloadInfo;

    @Override
    protected View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_bottom, null);
        ButterKnife.inject(this, rootView);
        mAppDetailBtnDownload.setOnClickListener(this);
        mAppDetailBtnFavour.setOnClickListener(this);
        mAppDetailBtnShare.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void setViewData(DetailBean data) {
        mDownloadManager = DownloadManager.getInstance();
        mDownloadInfo = mDownloadManager.getDownloadInfo(data);
        refreshButtonState(mDownloadInfo);
        mDownloadManager.addObserver(this);
    }

    private void refreshButtonState(DownloadInfoBean downloadInfo) {
        switch (downloadInfo.currentDownloadState) {
            case DownloadManager.STATE_NOT_DOWNLOAD:
                mAppDetailBtnDownload.setText(R.string.download_btn_download);
                break;
            case DownloadManager.STATE_WAIT_DOWNLOAD:
                mAppDetailBtnDownload.setText(R.string.download_btn_wait);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mAppDetailBtnDownload.setProgressButtonEnable(true);
                int progress = (int) (downloadInfo.progress * 100.f / downloadInfo.maxProgress + .5f);
                mAppDetailBtnDownload.setProgress(progress);
                String downloadProgress = UIUtils.getString(R.string.download_btn_downloading, progress + "%");
                mAppDetailBtnDownload.setText(downloadProgress);
                break;
            case DownloadManager.STATE_PAUSE_DOWNLOAD:
                mAppDetailBtnDownload.setProgressButtonEnable(true);
                int pausedProgress = (int) (downloadInfo.progress * 100.f / downloadInfo.maxProgress + .5f);
                mAppDetailBtnDownload.setProgress(pausedProgress);
                mAppDetailBtnDownload.setText(R.string.download_btn_pause);
                break;
            case DownloadManager.STATE_DOWNLOAD_SUCCESS:
                mAppDetailBtnDownload.setProgressButtonEnable(false);
                mAppDetailBtnDownload.setText(R.string.download_btn_install);
                mAppDetailBtnDownload.setBackgroundColor(Color.GREEN);
                break;
            case DownloadManager.STATE_DOWNLOAD_FAILED:
                mAppDetailBtnDownload.setText(R.string.download_btn_retry);
                break;
            case DownloadManager.STATE_INSTALLED:
                mAppDetailBtnDownload.setText(R.string.download_btn_open);
                mAppDetailBtnDownload.setBackgroundColor(Color.GREEN);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_detail_download_btn_download:
                respondClick(mDownloadInfo);
                break;
            case R.id.app_detail_download_btn_favo:
                break;
            case R.id.app_detail_download_btn_share:
                break;
        }
    }

    private void respondClick(DownloadInfoBean downloadInfo) {
        switch (downloadInfo.currentDownloadState) {
            case DownloadManager.STATE_NOT_DOWNLOAD:
                mDownloadManager.download(downloadInfo);
                break;
            case DownloadManager.STATE_WAIT_DOWNLOAD:
                mDownloadManager.cancelDownload(downloadInfo);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mDownloadManager.pauseDownload(downloadInfo);
                break;
            case DownloadManager.STATE_PAUSE_DOWNLOAD:
                mDownloadManager.download(downloadInfo);
                break;
            case DownloadManager.STATE_DOWNLOAD_SUCCESS:
                mDownloadManager.installApk(downloadInfo);
                break;
            case DownloadManager.STATE_DOWNLOAD_FAILED:
                mDownloadManager.download(downloadInfo);
                break;
            case DownloadManager.STATE_INSTALLED:
                mDownloadManager.openApp(downloadInfo);
                break;
        }
    }

    @Override
    public void onDownloadInfoChanged(DownloadInfoBean info) {
        if (info.packageName.equals(mDownloadInfo.packageName)) {
            mDownloadInfo = info;
            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    refreshButtonState(mDownloadInfo);
                }
            });
        }
    }
}
