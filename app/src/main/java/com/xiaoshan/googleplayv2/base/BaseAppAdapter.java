package com.xiaoshan.googleplayv2.base;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.xiaoshan.googleplayv2.activity.DetailActivity;
import com.xiaoshan.googleplayv2.bean.AppInfoBean;
import com.xiaoshan.googleplayv2.holder.AppItemHolder;
import com.xiaoshan.googleplayv2.utils.UIUtils;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/27.
 * 12:28
 */
public class BaseAppAdapter extends BaseSuperAdapter<AppInfoBean> {
    public BaseAppAdapter(AbsListView lv, List<AppInfoBean> data) {
        super(lv, data);
    }

    @Override
    public BaseViewHolder<AppInfoBean> getSpecificViewHolder(ViewGroup parent) {
        return new AppItemHolder(parent);
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        gotoDetailActivity(position);
    }

    private void gotoDetailActivity(int position) {
        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String packageName = mData.get(position).packageName;
        intent.putExtra("packageName", packageName);
        UIUtils.getContext().startActivity(intent);
    }
}
