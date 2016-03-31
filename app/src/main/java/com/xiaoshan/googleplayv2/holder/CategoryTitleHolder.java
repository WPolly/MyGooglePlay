package com.xiaoshan.googleplayv2.holder;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.CategoryInfoBean;
import com.xiaoshan.googleplayv2.utils.UIUtils;

/**
 * Created by xiaoshan on 2016/2/26.
 * 20:25
 */
public class CategoryTitleHolder extends BaseViewHolder<CategoryInfoBean> {

    private TextView mTv;

    @Override
    protected View initRootView() {
        mTv = new TextView(UIUtils.getContext());
        int paddingLeft = UIUtils.dip2Px(8);
        int padding = UIUtils.dip2Px(3);
        mTv.setPadding(paddingLeft,padding,padding,padding);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTv.setLayoutParams(params);
        mTv.setBackgroundResource(R.color.category_title_bg);
        mTv.setTextSize(12);
        return mTv;
    }

    @Override
    public void setViewData(CategoryInfoBean data) {
        mTv.setText(data.title);
    }
}
