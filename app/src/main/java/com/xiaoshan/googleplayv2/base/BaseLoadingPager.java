package com.xiaoshan.googleplayv2.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.factory.ThreadPoolFactory;
import com.xiaoshan.googleplayv2.utils.UIUtils;

/**
 * Created by xiaoshan on 2016/2/16.
 * 11:26
 */
public abstract class BaseLoadingPager extends FrameLayout {

    public static final int STATE_NONE = -1;
    public static final int STATE_LOADING = 0;
    public static final int STATE_EMPTY = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_SUCCESS = 3;

    private int mCurrentState = STATE_NONE;
    private View mLoadingPager;
    private View mErrorPager;
    private View mEmptyPager;
    private View mSuccessPager;

    public BaseLoadingPager(Context context) {
        super(context);
        initCommonView();
    }

    private void initCommonView() {
        mLoadingPager = View.inflate(getContext(), R.layout.pager_loading, null);
        addView(mLoadingPager);

        mErrorPager = View.inflate(getContext(), R.layout.pager_error, null);
        mErrorPager.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        addView(mErrorPager);

        mEmptyPager = View.inflate(getContext(), R.layout.pager_empty, null);
        addView(mEmptyPager);

        refreshUI();
    }

    private void refreshUI() {
        mLoadingPager.setVisibility(mCurrentState == STATE_LOADING || mCurrentState == STATE_NONE ? View.VISIBLE : View.INVISIBLE);
        mErrorPager.setVisibility(mCurrentState == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        mEmptyPager.setVisibility(mCurrentState == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);

        if (mSuccessPager == null && mCurrentState == STATE_SUCCESS) {
            mSuccessPager = initSuccessView();
            addView(mSuccessPager);
        }
    }

    protected abstract View initSuccessView();

    public void loadData() {
        if (mCurrentState != STATE_LOADING && mCurrentState != STATE_SUCCESS) {
            mCurrentState = STATE_LOADING;
            refreshUI();
            ThreadPoolFactory.getNormalThreadPool().getThreadPoolExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    LoadedResult loadedResult = initData();
                    mCurrentState = loadedResult.getState();
                    UIUtils.postTaskSafely(new Runnable() {
                        @Override
                        public void run() {
                            refreshUI();
                        }
                    });
                }
            });
        }
    }

    protected abstract LoadedResult initData();

    public enum LoadedResult {

        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);

        private int state;

        LoadedResult(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

}
