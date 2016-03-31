package com.xiaoshan.googleplayv2.base;

import android.view.View;

/**
 * Created by xiaoshan on 2016/2/19.
 * 12:48
 * 这里所谓的VeiwHolder其实是根部局的容器
 */
public abstract class BaseViewHolder<T> {

    private View mHolderView;

    public BaseViewHolder() {
        mHolderView = initRootView();
        mHolderView.setTag(this);
    }

    protected abstract View initRootView();

    public abstract void setViewData(T data);

    public View getRootView() {
        return mHolderView;
    }
}
