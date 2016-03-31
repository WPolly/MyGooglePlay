package com.xiaoshan.googleplayv2.fragment;

import android.view.View;
import android.widget.ListView;

import com.xiaoshan.googleplayv2.adapter.AppAdapter;
import com.xiaoshan.googleplayv2.base.BaseFragment;
import com.xiaoshan.googleplayv2.base.BaseLoadingPager;
import com.xiaoshan.googleplayv2.bean.AppInfoBean;
import com.xiaoshan.googleplayv2.factory.ListViewFactory;
import com.xiaoshan.googleplayv2.protocol.AppProtocol;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/15.
 * 16:46
 */
public class AppFragment extends BaseFragment {

    private List<AppInfoBean> mAppData;

    @Override
    public View initSuccessView() {
        ListView lv = ListViewFactory.getListViewInstance();
        lv.setAdapter(new AppAdapter(lv,mAppData));
        return lv;
    }

    @Override
    public BaseLoadingPager.LoadedResult initData() {
        AppProtocol appProtocol = new AppProtocol();
        try {
            mAppData = appProtocol.loadData(0);

            if (mAppData == null || mAppData.size() ==0 ) {
                return BaseLoadingPager.LoadedResult.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseLoadingPager.LoadedResult.ERROR;
        }
        return BaseLoadingPager.LoadedResult.SUCCESS;
    }

}
