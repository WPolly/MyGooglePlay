package com.xiaoshan.googleplayv2.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoshan.googleplayv2.R;
import com.xiaoshan.googleplayv2.base.BaseViewHolder;
import com.xiaoshan.googleplayv2.bean.CategoryInfoBean;
import com.xiaoshan.googleplayv2.utils.BitmapHelper;
import com.xiaoshan.googleplayv2.utils.UIUtils;

/**
 * Created by xiaoshan on 2016/2/26.
 * 15:18
 */
public class CategoryHolder extends BaseViewHolder<CategoryInfoBean> implements View.OnClickListener {

    private View mItemView1;
    private View mItemView2;
    private View mItemView3;
    private ImageView mIcon1;
    private ImageView mIcon2;
    private ImageView mIcon3;
    private TextView mName1;
    private TextView mName2;
    private TextView mName3;

    @Override
    protected View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_category, null);
        mItemView1 = rootView.findViewById(R.id.item_category_item_1);
        mItemView2 = rootView.findViewById(R.id.item_category_item_2);
        mItemView3 = rootView.findViewById(R.id.item_category_item_3);
        mIcon1 = (ImageView) rootView.findViewById(R.id.item_category_icon_1);
        mIcon2 = (ImageView) rootView.findViewById(R.id.item_category_icon_2);
        mIcon3 = (ImageView) rootView.findViewById(R.id.item_category_icon_3);
        mName1 = (TextView) rootView.findViewById(R.id.item_category_name_1);
        mName2 = (TextView) rootView.findViewById(R.id.item_category_name_2);
        mName3 = (TextView) rootView.findViewById(R.id.item_category_name_3);
        return rootView;
    }

    @Override
    public void setViewData(CategoryInfoBean data) {
        checkAndSetData(mName1,data.name1,mIcon1,data.url1);
        checkAndSetData(mName2,data.name2,mIcon2,data.url2);
        checkAndSetData(mName3,data.name3,mIcon3,data.url3);
        mItemView1.setOnClickListener(this);
        mItemView2.setOnClickListener(this);
        mItemView3.setOnClickListener(this);
    }
    
    private void checkAndSetData(TextView tvName, String name, ImageView ivIcon, String url) {
        if (!TextUtils.isEmpty(url)) {
            BitmapHelper.display(ivIcon,url);
            tvName.setText(name);
            ((ViewGroup) ivIcon.getParent()).setVisibility(View.VISIBLE);
        } else {
            ((ViewGroup) ivIcon.getParent()).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        String name = ((TextView) ((ViewGroup) v).getChildAt(1)).getText().toString();
        Toast.makeText(UIUtils.getContext(), name, Toast.LENGTH_SHORT).show();
    }
}
