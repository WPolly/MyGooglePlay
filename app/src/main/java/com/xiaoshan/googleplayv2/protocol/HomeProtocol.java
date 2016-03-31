package com.xiaoshan.googleplayv2.protocol;

import com.google.gson.Gson;
import com.xiaoshan.googleplayv2.base.BaseProtocol;
import com.xiaoshan.googleplayv2.bean.HomeInfoBean;

/**
 * Created by xiaoshan on 2016/2/22.
 * 21:09
 */
public class HomeProtocol extends BaseProtocol<HomeInfoBean>{

    @Override
    protected String getInterfaceKey() {
        return "home";
    }

    @Override
    protected HomeInfoBean parseJson(String jsonString) {
        Gson gson = new Gson();
        HomeInfoBean homeInfoBean = gson.fromJson(jsonString, HomeInfoBean.class);
        return homeInfoBean;
    }
}
