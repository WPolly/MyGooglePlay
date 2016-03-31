package com.xiaoshan.googleplayv2.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.SubjectInfoBean;
import com.xiaoshan.googleplayv2.utils.BitmapHelper;
import com.xiaoshan.googleplayv2.utils.UIUtils;

/**
 * Created by xiaoshan on 2016/2/25.
 * 13:33
 */
public class SubjectViewHolder extends BaseViewHolder<SubjectInfoBean> {

    private ImageView mSubjectPicture;
    private TextView mPictureTitle;

    @Override
    protected View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_subject_info, null);
        mSubjectPicture = (ImageView) rootView.findViewById(R.id.item_subject_iv_icon);
        mPictureTitle = (TextView) rootView.findViewById(R.id.item_subject_tv_title);
        return rootView;
    }

    @Override
    public void setViewData(SubjectInfoBean data) {
        BitmapHelper.display(mSubjectPicture,data.url);
        mPictureTitle.setText(data.des);
    }
}
