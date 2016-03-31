package com.xiaoshan.googleplayv2.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaoshan.googleplayv2.adapter.CategoryAdapter;
import com.xiaoshan.googleplayv2.base.BaseFragment;
import com.xiaoshan.googleplayv2.base.BaseLoadingPager;
import com.xiaoshan.googleplayv2.bean.CategoryInfoBean;
import com.xiaoshan.googleplayv2.factory.ListViewFactory;
import com.xiaoshan.googleplayv2.protocol.CategoryProtocol;
import com.xiaoshan.googleplayv2.utils.LogUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by xiaoshan on 2016/2/15.
 * 16:46
 */
public class CategoryFragment extends BaseFragment {
    private List<CategoryInfoBean> mData;
    @Override
    public View initSuccessView() {
        ListView lv = ListViewFactory.getListViewInstance();
        lv.setAdapter(new CategoryAdapter(lv,mData));
        return lv;
    }

    @Override
    public BaseLoadingPager.LoadedResult initData() {
        CategoryProtocol protocol = new CategoryProtocol();
        try {
            mData = protocol.loadData(0);
            LogUtils.printList(mData);
            if (mData == null || mData.size() == 0) {
                return BaseLoadingPager.LoadedResult.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseLoadingPager.LoadedResult.ERROR;
        }
        return BaseLoadingPager.LoadedResult.SUCCESS;
    }
}
