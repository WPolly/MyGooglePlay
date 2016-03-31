package com.xiaoshan.googleplayv2.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaoshan.googleplayv2.base.BaseProtocol;
import com.xiaoshan.googleplayv2.bean.SubjectInfoBean;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/25.
 * 12:58
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfoBean>> {

    @Override
    protected String getInterfaceKey() {
        return "subject";
    }

    @Override
    protected List<SubjectInfoBean> parseJson(String jsonString) {
        return new Gson().fromJson(jsonString, new TypeToken<List<SubjectInfoBean>>(){}.getType());
    }
}
