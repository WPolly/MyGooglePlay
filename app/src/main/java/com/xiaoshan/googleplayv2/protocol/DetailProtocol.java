package com.xiaoshan.googleplayv2.protocol;

import com.google.gson.Gson;
import com.xiaoshan.googleplayv2.base.BaseProtocol;
import com.xiaoshan.googleplayv2.bean.DetailBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoshan on 2016/2/27.
 * 20:41
 */
public class DetailProtocol extends BaseProtocol<DetailBean> {
    private String mPackageName;

    public DetailProtocol(String packageName) {
        mPackageName = packageName;
    }

    @Override
    protected String getInterfaceKey() {
        return "detail";
    }

    @Override
    protected DetailBean parseJson(String jsonString) {
        return new Gson().fromJson(jsonString, DetailBean.class);
    }

    @Override
    protected Map<String, String> getExtraParams() {
        Map<String,String> extraParams = new HashMap<>();
        extraParams.put("packageName", mPackageName);
        return extraParams;
    }
}
