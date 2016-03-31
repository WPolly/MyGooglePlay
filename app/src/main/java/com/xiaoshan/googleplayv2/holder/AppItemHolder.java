package com.xiaoshan.googleplayv2.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.AppInfoBean;
import com.xiaoshan.googleplayv2.manager.DownloadManager;
import com.xiaoshan.googleplayv2.manager.bean.DownloadInfoBean;
import com.xiaoshan.googleplayv2.utils.BitmapHelper;
import com.xiaoshan.googleplayv2.utils.StringUtils;
import com.xiaoshan.googleplayv2.utils.UIUtils;
import com.xiaoshan.googleplayv2.view.CircleProgressView;


/**
 * Created by xiaoshan on 2016/2/19.
 * 16:52
 */
public class AppItemHolder extends BaseViewHolder<AppInfoBean> implements View.OnClickListener, DownloadManager.DownLoadObserver {


    private ImageView mItemAppInfoIvIcon;
    private TextView mItemAppInfoTvTitle;
    private RatingBar mItemAppInfoRbStars;
    private TextView mItemAppInfoTvSize;
    private TextView mItemAppInfoTvDes;
    private ViewGroup mParent;
    private CircleProgressView mCircleProgressView;

    private DownloadManager mDownloadManager;


    private DownloadInfoBean mDownloadInfo;

    public AppItemHolder(ViewGroup parent) {
        mParent = parent;
    }

    @Override
    public View initRootView() {
        View rootView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_app_info, mParent, false);
        mItemAppInfoTvTitle = ((TextView) rootView.findViewById(R.id.item_appinfo_tv_title));
        mItemAppInfoTvDes = ((TextView) rootView.findViewById(R.id.item_appinfo_tv_des));
        mItemAppInfoRbStars = (RatingBar) rootView.findViewById(R.id.item_appinfo_rb_stars);
        mItemAppInfoTvSize = ((TextView) rootView.findViewById(R.id.item_appinfo_tv_size));
        mItemAppInfoIvIcon = (ImageView) rootView.findViewById(R.id.item_appinfo_iv_icon);
        mCircleProgressView = ((CircleProgressView) rootView.findViewById(R.id.cpv));
        mCircleProgressView.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void setViewData(AppInfoBean data) {
        mItemAppInfoTvTitle.setText(data.name);
        mItemAppInfoRbStars.setRating(data.stars);
        mItemAppInfoTvDes.setText(data.des);
        mItemAppInfoTvSize.setText(StringUtils.formatFileSize(data.size));
        BitmapHelper.display(mItemAppInfoIvIcon, data.iconUrl);

        mCircleProgressView.setProgress(0);
        mDownloadManager = DownloadManager.getInstance();
        mDownloadInfo = mDownloadManager.getDownloadInfo(data);
        refreshButtonState(mDownloadInfo);
        mDownloadManager.addObserver(this);
    }

    private void refreshButtonState(DownloadInfoBean downloadInfo) {
        switch (downloadInfo.currentDownloadState) {
            case DownloadManager.STATE_NOT_DOWNLOAD:
                mCircleProgressView.setCircleProgressNote(R.string.download_btn_download);
                mCircleProgressView.setCircleProgressIconRes(R.mipmap.ic_download);
                break;
            case DownloadManager.STATE_WAIT_DOWNLOAD:
                mCircleProgressView.setCircleProgressNote(R.string.download_btn_wait);
                mCircleProgressView.setCircleProgressIconRes(R.mipmap.ic_cancel);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mCircleProgressView.setCircleProgressEnable(true);
                int progress = (int) (downloadInfo.progress * 100.f / downloadInfo.maxProgress + .5f);
                mCircleProgressView.setProgress(progress);
                mCircleProgressView.setCircleProgressNote(progress + "%");
                mCircleProgressView.setCircleProgressIconRes(R.mipmap.ic_pause);
                break;
            case DownloadManager.STATE_PAUSE_DOWNLOAD:
                mCircleProgressView.setCircleProgressEnable(true);
                int pausedProgress = (int) (downloadInfo.progress * 100.f / downloadInfo.maxProgress + .5f);
                mCircleProgressView.setProgress(pausedProgress);
                mCircleProgressView.setCircleProgressNote(R.string.download_btn_pause);
                mCircleProgressView.setCircleProgressIconRes(R.mipmap.ic_resume);
                break;
            case DownloadManager.STATE_DOWNLOAD_SUCCESS:
                mCircleProgressView.setCircleProgressEnable(false);
                mCircleProgressView.setCircleProgressNote(R.string.download_btn_install);
                mCircleProgressView.setCircleProgressIconRes(R.mipmap.ic_install);
                break;
            case DownloadManager.STATE_DOWNLOAD_FAILED:
                mCircleProgressView.setCircleProgressNote(R.string.download_btn_retry);
                mCircleProgressView.setCircleProgressIconRes(R.mipmap.ic_redownload);
                break;
            case DownloadManager.STATE_INSTALLED:
                mCircleProgressView.setCircleProgressNote(R.string.download_btn_open);
                mCircleProgressView.setCircleProgressIconRes(R.mipmap.ic_install);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (mDownloadInfo.currentDownloadState) {
            case DownloadManager.STATE_NOT_DOWNLOAD:
                mDownloadManager.download(mDownloadInfo);
                break;
            case DownloadManager.STATE_WAIT_DOWNLOAD:
                mDownloadManager.cancelDownload(mDownloadInfo);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mDownloadManager.pauseDownload(mDownloadInfo);
                break;
            case DownloadManager.STATE_PAUSE_DOWNLOAD:
                mDownloadManager.download(mDownloadInfo);
                break;
            case DownloadManager.STATE_DOWNLOAD_SUCCESS:
                mDownloadManager.installApk(mDownloadInfo);
                break;
            case DownloadManager.STATE_DOWNLOAD_FAILED:
                mDownloadManager.download(mDownloadInfo);
                break;
            case DownloadManager.STATE_INSTALLED:
                mDownloadManager.openApp(mDownloadInfo);
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

    public DownloadInfoBean getDownloadInfo() {
        return mDownloadInfo;
    }

}
