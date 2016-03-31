package com.xiaoshan.googleplayv2.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaoshan.googleplayv2.adapter.SubjectAdapter;
import com.xiaoshan.googleplayv2.base.BaseFragment;
import com.xiaoshan.googleplayv2.base.BaseLoadingPager;
import com.xiaoshan.googleplayv2.bean.SubjectInfoBean;
import com.xiaoshan.googleplayv2.factory.ListViewFactory;
import com.xiaoshan.googleplayv2.protocol.SubjectProtocol;

import java.util.List;
import java.util.Random;

/**
 * Created by xiaoshan on 2016/2/15.
 * 16:46
 */
public class SubjectFragment extends BaseFragment {

    private List<SubjectInfoBean> mData;

    @Override
    public View initSuccessView() {
        ListView lv = ListViewFactory.getListViewInstance();
        lv.setAdapter(new SubjectAdapter(lv,mData));
        return lv;
    }

    @Override
    public BaseLoadingPager.LoadedResult initData() {
        SubjectProtocol protocol = new SubjectProtocol();
        try {
            mData = protocol.loadData(0);
            if (mData ==null || mData.size() == 0) {
                return BaseLoadingPager.LoadedResult.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseLoadingPager.LoadedResult.ERROR;
        }
        return BaseLoadingPager.LoadedResult.SUCCESS;
    }
}
