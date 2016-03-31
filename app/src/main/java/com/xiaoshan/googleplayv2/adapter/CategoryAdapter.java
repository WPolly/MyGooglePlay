package com.xiaoshan.googleplayv2.adapter;

import android.view.ViewGroup;
import android.widget.AbsListView;

import com.xiaoshan.googleplayv2.base.BaseSuperAdapter;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.CategoryInfoBean;
import com.xiaoshan.googleplayv2.holder.CategoryHolder;
import com.xiaoshan.googleplayv2.holder.CategoryTitleHolder;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/26.
 * 12:54
 */
public class CategoryAdapter extends BaseSuperAdapter<CategoryInfoBean> {
    private static final int VIEW_TYPE_CATEGORY_TITLE = 2;

    public CategoryAdapter(AbsListView lv, List<CategoryInfoBean> data) {
        super(lv, data);
    }

    @Override
    public BaseViewHolder<CategoryInfoBean> getSpecificViewHolder(ViewGroup parent) {
        if (mData.get(mPosition).isTitleView) {
            return new CategoryTitleHolder();
        } else {
            return new CategoryHolder();
        }
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).isTitleView) {
            return VIEW_TYPE_CATEGORY_TITLE;
        }
        return super.getItemViewType(position);
    }

}
