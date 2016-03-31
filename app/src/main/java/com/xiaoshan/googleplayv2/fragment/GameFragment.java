package com.xiaoshan.googleplayv2.fragment;

import android.view.View;
import android.widget.ListView;

import com.xiaoshan.googleplayv2.adapter.GameAdapter;
import com.xiaoshan.googleplayv2.base.BaseFragment;
import com.xiaoshan.googleplayv2.base.BaseLoadingPager;
import com.xiaoshan.googleplayv2.bean.AppInfoBean;
import com.xiaoshan.googleplayv2.factory.ListViewFactory;
import com.xiaoshan.googleplayv2.protocol.GameProtocol;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/15.
 * 16:46
 */
public class GameFragment extends BaseFragment {

    private List<AppInfoBean> mGameData;

    @Override
    public View initSuccessView() {
        ListView lv = ListViewFactory.getListViewInstance();
        lv.setAdapter(new GameAdapter(lv, mGameData));
        return lv;
    }

    @Override
    public BaseLoadingPager.LoadedResult initData() {
        GameProtocol gameProtocol = new GameProtocol();
        try {
            mGameData = gameProtocol.loadData(0);

            if (mGameData == null || mGameData.size() ==0 ) {
                return BaseLoadingPager.LoadedResult.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseLoadingPager.LoadedResult.ERROR;
        }
        return BaseLoadingPager.LoadedResult.SUCCESS;
    }
}
