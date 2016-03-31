package com.xiaoshan.googleplayv2.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaoshan.googleplayv2.base.BaseProtocol;
import com.xiaoshan.googleplayv2.bean.AppInfoBean;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/23.
 * 16:08
 */
public class AppProtocol extends BaseProtocol<List<AppInfoBean>> {
    @Override
    protected String getInterfaceKey() {
        return "app";
    }

    @Override
    protected List<AppInfoBean> parseJson(String jsonString) {
        return new Gson().fromJson(jsonString, new TypeToken<List<AppInfoBean>>(){}.getType());
    }
}
