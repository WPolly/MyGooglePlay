package com.xiaoshan.googleplayv2.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaoshan.googleplayv2.base.BaseProtocol;

import java.util.List;

/**
 * Created by xiaoshan on 2016/2/27.
 * 10:08
 */
public class RecommendProtocol extends BaseProtocol<List<String>> {

    @Override
    protected String getInterfaceKey() {
        return "recommend";
    }

    @Override
    protected List<String> parseJson(String jsonString) {
        return new Gson().fromJson(jsonString, new TypeToken<List<String>>(){}.getType());
    }
}
