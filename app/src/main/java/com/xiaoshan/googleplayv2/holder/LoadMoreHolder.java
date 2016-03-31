package com.xiaoshan.googleplayv2.holder;

import android.view.View;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.utils.UIUtils;

/**
 * Created by xiaoshan on 2016/2/21.
 * 19:27
 */
public class LoadMoreHolder extends BaseViewHolder<Integer> {

    public static final int STATE_NONE = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_RETRY = 2;

    private View mLoadingView;
    private View mRetryView;

    @Override
    public View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_load_more, null);
        mLoadingView = rootView.findViewById(R.id.item_loadmore_container_loading);
        mRetryView = rootView.findViewById(R.id.item_loadmore_container_retry);
        return rootView;
    }

    @Override
    public void setViewData(Integer data) {
        mLoadingView.setVisibility(View.GONE);
        mRetryView.setVisibility(View.GONE);

        switch (data) {
            case STATE_NONE:
                break;

            case STATE_LOADING:
                mLoadingView.setVisibility(View.VISIBLE);
                break;

            case STATE_RETRY:
                mRetryView.setVisibility(View.VISIBLE);
                break;
        }
    }
}
