package com.xiaoshan.googleplayv2.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.DetailBean;
import com.xiaoshan.googleplayv2.utils.BitmapHelper;
import com.xiaoshan.googleplayv2.utils.StringUtils;
import com.xiaoshan.googleplayv2.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaoshan on 2016/2/27.
 * 22:08
 */
public class AppDetailInfoHolder extends BaseViewHolder<DetailBean> {
    @InjectView(R.id.app_detail_info_iv_icon)
    ImageView mAppDetailInfoIvIcon;
    @InjectView(R.id.app_detail_info_tv_name)
    TextView mAppDetailInfoTvName;
    @InjectView(R.id.app_detail_info_rb_star)
    RatingBar mAppDetailInfoRbStar;
    @InjectView(R.id.app_detail_info_tv_downloadnum)
    TextView mAppDetailInfoTvDownloadnum;
    @InjectView(R.id.app_detail_info_tv_version)
    TextView mAppDetailInfoTvVersion;
    @InjectView(R.id.app_detail_info_tv_time)
    TextView mAppDetailInfoTvTime;
    @InjectView(R.id.app_detail_info_tv_size)
    TextView mAppDetailInfoTvSize;

    @Override
    protected View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_info, null);
        ButterKnife.inject(this,rootView);
        return rootView;
    }

    @Override
    public void setViewData(DetailBean data) {
        String downloadNum = UIUtils.getString(R.string.app_detail_download_num, data.downloadNum);
        String version = UIUtils.getString(R.string.app_detail_version, data.version);
        String date = UIUtils.getString(R.string.app_detail_date, data.date);
        String size = UIUtils.getString(R.string.app_detail_size, StringUtils.formatFileSize(data.size));
        mAppDetailInfoTvName.setText(data.name);
        mAppDetailInfoRbStar.setRating(data.stars);
        mAppDetailInfoTvDownloadnum.setText(downloadNum);
        mAppDetailInfoTvSize.setText(size);
        mAppDetailInfoTvTime.setText(date);
        mAppDetailInfoTvVersion.setText(version);
        BitmapHelper.display(mAppDetailInfoIvIcon,data.iconUrl);
    }
}
