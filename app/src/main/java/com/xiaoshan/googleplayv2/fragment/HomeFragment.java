package com.xiaoshan.googleplayv2.fragment;

import android.view.View;
import android.widget.ListView;

import com.xiaoshan.googleplayv2.adapter.HomeAdapter;
import com.xiaoshan.googleplayv2.base.BaseFragment;
import com.xiaoshan.googleplayv2.base.BaseLoadingPager;
import com.xiaoshan.googleplayv2.bean.HomeInfoBean;
import com.xiaoshan.googleplayv2.factory.ListViewFactory;
import com.xiaoshan.googleplayv2.holder.CarouselPictureViewHolder;
import com.xiaoshan.googleplayv2.protocol.HomeProtocol;

/**
 * Created by xiaoshan on 2016/2/15.
 * 16:46
 */
public class HomeFragment extends BaseFragment {

    private HomeInfoBean mHomeData;
    private HomeAdapter mAdapter;

    @Override
    public View initSuccessView() {
        ListView lv = ListViewFactory.getListViewInstance();

        if (mHomeData != null) {
            mAdapter = new HomeAdapter(lv, mHomeData.list);
            lv.setAdapter(mAdapter);
            CarouselPictureViewHolder carouselPictureViewHolder = new CarouselPictureViewHolder();
            carouselPictureViewHolder.setViewData(mHomeData);
            View carouselPictureView = carouselPictureViewHolder.getRootView();
            lv.addHeaderView(carouselPictureView);
        }
        return lv;
    }

    @Override
    public BaseLoadingPager.LoadedResult initData() {
        HomeProtocol homeProtocol = new HomeProtocol();
        try {
            HomeInfoBean homeInfoBean = homeProtocol.loadData(0);
            if (homeInfoBean == null) {
                return BaseLoadingPager.LoadedResult.EMPTY;
            }

            mHomeData = homeInfoBean;

            if (mHomeData.list == null || mHomeData.list.size() == 0) {
                return BaseLoadingPager.LoadedResult.EMPTY;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return BaseLoadingPager.LoadedResult.ERROR;
        }

        return BaseLoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
