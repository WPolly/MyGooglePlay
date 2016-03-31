package com.xiaoshan.googleplayv2.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseActivity;
import com.xiaoshan.googleplayv2.base.BaseLoadingPager;
import com.xiaoshan.googleplayv2.bean.DetailBean;
import com.xiaoshan.googleplayv2.holder.AppDetailDesHolder;
import com.xiaoshan.googleplayv2.holder.AppDetailDownloadHolder;
import com.xiaoshan.googleplayv2.holder.AppDetailInfoHolder;
import com.xiaoshan.googleplayv2.holder.AppDetailPicHolder;
import com.xiaoshan.googleplayv2.holder.AppDetailSafeHolder;
import com.xiaoshan.googleplayv2.manager.DownloadManager;
import com.xiaoshan.googleplayv2.manager.bean.DownloadInfoBean;
import com.xiaoshan.googleplayv2.protocol.DetailProtocol;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailActivity extends BaseActivity {

    @InjectView(R.id.app_detail_bottom)
    FrameLayout mAppDetailBottom;
    @InjectView(R.id.app_detail_info)
    FrameLayout mAppDetailInfo;
    @InjectView(R.id.app_detail_safe)
    FrameLayout mAppDetailSafe;
    @InjectView(R.id.app_detail_pic)
    FrameLayout mAppDetailPic;
    @InjectView(R.id.app_detail_des)
    FrameLayout mAppDetailDes;

    private BaseLoadingPager mLoadingPager;
    private String mPackageName;
    private View mViewDetail;
    private DetailBean mDetailBean;
    private AppDetailDownloadHolder mAppDetailDownloadHolder;

    @Override
    protected void init() {
        mPackageName = getIntent().getStringExtra("packageName");
    }

    @Override
    protected void initView() {
        mViewDetail = View.inflate(this, R.layout.activity_detail, null);
        ButterKnife.inject(this,mViewDetail);
        initToolbar();
        initDataForView();
        setContentView(mLoadingPager);
    }

    private void initDataForView() {
        mLoadingPager = new BaseLoadingPager(this) {
            @Override
            protected View initSuccessView() {
                AppDetailInfoHolder appDetailInfoHolder = new AppDetailInfoHolder();
                appDetailInfoHolder.setViewData(mDetailBean);
                mAppDetailInfo.addView(appDetailInfoHolder.getRootView());

                AppDetailSafeHolder appDetailSafeHolder = new AppDetailSafeHolder();
                appDetailSafeHolder.setViewData(mDetailBean);
                mAppDetailSafe.addView(appDetailSafeHolder.getRootView());

                AppDetailPicHolder appDetailPicHolder = new AppDetailPicHolder();
                appDetailPicHolder.setViewData(mDetailBean);
                mAppDetailPic.addView(appDetailPicHolder.getRootView());

                AppDetailDesHolder appDetailDesHolder = new AppDetailDesHolder();
                appDetailDesHolder.setViewData(mDetailBean);
                mAppDetailDes.addView(appDetailDesHolder.getRootView());

                mAppDetailDownloadHolder = new AppDetailDownloadHolder();
                mAppDetailBottom.addView(mAppDetailDownloadHolder.getRootView());
                mAppDetailDownloadHolder.setViewData(mDetailBean);

                return mViewDetail;
            }

            @Override
            protected LoadedResult initData() {
                DetailProtocol protocol = new DetailProtocol(mPackageName);
                try {
                    mDetailBean = protocol.loadData(0);
                    if (mDetailBean == null) {
                        return LoadedResult.EMPTY;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return LoadedResult.ERROR;
                }
                return LoadedResult.SUCCESS;
            }
        };

        mLoadingPager.loadData();
    }

    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) mViewDetail.findViewById(R.id.main_toolbar));
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(R.string.detai_title);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DownloadManager instance = DownloadManager.getInstance();
        if (mAppDetailDownloadHolder != null) {
            instance.addObserver(mAppDetailDownloadHolder);
            DownloadInfoBean downloadInfo = instance.getDownloadInfo(mDetailBean);
            instance.notifyObservers(downloadInfo);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        DownloadManager.getInstance().deleteObserver(mAppDetailDownloadHolder);
    }
}
