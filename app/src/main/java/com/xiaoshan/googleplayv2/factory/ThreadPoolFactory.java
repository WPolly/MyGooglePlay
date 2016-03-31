package com.xiaoshan.googleplayv2.factory;

import com.xiaoshan.googleplayv2.manager.ThreadPoolProxy;

/**
 * Created by xiaoshan on 2016/2/18.
 * 19:53
 */
public class ThreadPoolFactory {

    private static ThreadPoolProxy mNormalThreadPool;
    private static ThreadPoolProxy mDownLoadThreadPool;

    public static ThreadPoolProxy getNormalThreadPool() {
        if (mNormalThreadPool == null) {
            synchronized (ThreadPoolProxy.class) {
                if (mNormalThreadPool == null) {
                    mNormalThreadPool = new ThreadPoolProxy(5, 5, 3000);
                }
            }
        }

        return mNormalThreadPool;
    }

    public static ThreadPoolProxy getDownLoadThreadPool() {
        if (mDownLoadThreadPool == null) {
            synchronized (ThreadPoolProxy.class) {
                if (mDownLoadThreadPool == null) {
                    mDownLoadThreadPool = new ThreadPoolProxy(3, 3, 3000);
                }
            }
        }

        return mDownLoadThreadPool;
    }
}
