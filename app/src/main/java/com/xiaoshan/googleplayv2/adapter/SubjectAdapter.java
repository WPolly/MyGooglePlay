package com.xiaoshan.googleplayv2.adapter;

import android.view.ViewGroup;
import android.widget.AbsListView;

import com.xiaoshan.googleplayv2.base.BaseSuperAdapter;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.SubjectInfoBean;
import com.xiaoshan.googleplayv2.holder.SubjectViewHolder;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/25.
 * 13:30
 */
public class SubjectAdapter extends BaseSuperAdapter<SubjectInfoBean> {
    public SubjectAdapter(AbsListView lv, List<SubjectInfoBean> data) {
        super(lv, data);
    }

    @Override
    public BaseViewHolder<SubjectInfoBean> getSpecificViewHolder(ViewGroup parent) {
        return new SubjectViewHolder();
    }

    @Override
    protected boolean hasLoadMore() {
        return false;
    }
}
