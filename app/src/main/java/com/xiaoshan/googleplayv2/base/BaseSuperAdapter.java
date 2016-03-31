package com.xiaoshan.googleplayv2.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xiaoshan.googleplayv2.config.Constants;
import com.xiaoshan.googleplayv2.factory.ThreadPoolFactory;
import com.xiaoshan.googleplayv2.holder.LoadMoreHolder;
import com.xiaoshan.googleplayv2.utils.UIUtils;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/19.
 * 12:37
 */
public abstract class BaseSuperAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener {

    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_LOAD_MORE = 1;
    private AbsListView mLv;

    protected List<T> mData;
    private BaseViewHolder<Integer> mLoadMoreView;
    private LoadMoreTask mLoadMoreTask;
    protected int mPosition;

    public BaseSuperAdapter(AbsListView lv, List<T> data) {
        mData = data;
        mLv = lv;
        mLv.setOnItemClickListener(this);
    }

    @Override
    public int getCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mData != null) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return VIEW_TYPE_LOAD_MORE;
        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mPosition = position;
        BaseViewHolder<T> baseViewHolder;
        if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE) {
            mLoadMoreView = getLoadMoreView();
            convertView = mLoadMoreView.getRootView();
            if (hasLoadMore()) {
                mLoadMoreView.setViewData(LoadMoreHolder.STATE_LOADING);
                performGetMoreData();
            } else {
                mLoadMoreView.setViewData(LoadMoreHolder.STATE_NONE);
            }
        } else {
            if (convertView == null) {
                baseViewHolder = getSpecificViewHolder(parent);
                convertView = baseViewHolder.getRootView();
            } else {
                baseViewHolder = (BaseViewHolder<T>) convertView.getTag();
            }
            baseViewHolder.setViewData(mData.get(position));
        }
        return convertView;
    }

    /**
     * 触发加载更多数据
     */
    private void performGetMoreData() {
        if (mLoadMoreTask == null) {
            mLoadMoreTask = new LoadMoreTask();
        }
        ThreadPoolFactory.getNormalThreadPool().getThreadPoolExecutor().execute(mLoadMoreTask);
    }


    /**
     * 加载更多的任务
     */
    class LoadMoreTask implements Runnable {

        private int mState;
        private List<T> mMoreData;

        @Override
        public void run() {
            mState = LoadMoreHolder.STATE_LOADING;
            try {
                mMoreData = loadMoreData();
                if (mMoreData == null) {
                    mState = LoadMoreHolder.STATE_NONE;
                } else {
                    if (mMoreData.size() < Constants.PAGE_SIZE) {
                        mState = LoadMoreHolder.STATE_NONE;
                    } else {
                        mState = LoadMoreHolder.STATE_LOADING;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mState = LoadMoreHolder.STATE_RETRY;
            }

            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    mLoadMoreView.setViewData(mState);
                    if (mMoreData != null) {
                        mData.addAll(mMoreData);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    /**
     * 加载更多具体操作,根据实际需要覆写
     *
     */
    protected List<T> loadMoreData() throws Exception {
        return null;
    }

    /**
     * 判断该页面有没有加载更多的需求
     *
     */
    protected boolean hasLoadMore() {
        return true;
    }

    /**
     * 获取加载更多的视图
     *
     */
    private BaseViewHolder<Integer> getLoadMoreView() {
        if (mLoadMoreView == null) {
            return new LoadMoreHolder();
        }
        return mLoadMoreView;
    }


    /**
     * 得到特定的ViewHolder
     *
     */
    public abstract BaseViewHolder<T> getSpecificViewHolder(ViewGroup parent);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE) {
            performGetMoreData();
        } else {
            if (mLv instanceof ListView) {
                int headerViewsCount = ((ListView) mLv).getHeaderViewsCount();
                position -= headerViewsCount;
            }
            onNormalItemClick(parent, view, position, id);
        }
    }

    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
