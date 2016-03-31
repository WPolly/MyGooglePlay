package com.xiaoshan.googleplayv2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaoshan.googleplayv2.base.BaseAppAdapter;
import com.xiaoshan.googleplayv2.base.BaseSuperAdapter;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.AppInfoBean;
import com.xiaoshan.googleplayv2.holder.AppItemHolder;
import com.xiaoshan.googleplayv2.protocol.HomeProtocol;
import com.xiaoshan.googleplayv2.utils.UIUtils;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/19.
 * 12:32
 */
public class HomeAdapter extends BaseAppAdapter {

    public HomeAdapter(AbsListView lv, List<AppInfoBean> data) {
        super(lv, data);
    }

    @Override
    protected List<AppInfoBean> loadMoreData() throws Exception {
        Thread.sleep(500);
        HomeProtocol homeProtocol = new HomeProtocol();
        return homeProtocol.loadData(getCount()).list;
    }
}
