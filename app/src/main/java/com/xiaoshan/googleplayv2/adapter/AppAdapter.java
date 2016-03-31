package com.xiaoshan.googleplayv2.adapter;

import android.widget.AbsListView;

import com.xiaoshan.googleplayv2.base.BaseAppAdapter;
import com.xiaoshan.googleplayv2.bean.AppInfoBean;
import com.xiaoshan.googleplayv2.protocol.AppProtocol;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/23.
 * 16:24
 */
public class AppAdapter extends BaseAppAdapter {
    public AppAdapter(AbsListView lv, List<AppInfoBean> data) {
        super(lv, data);
    }

    @Override
    protected List<AppInfoBean> loadMoreData() throws Exception {
        Thread.sleep(500);
        return new AppProtocol().loadData(getCount());
    }
}
