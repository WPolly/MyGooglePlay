package com.xiaoshan.googleplayv2.protocol;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.xiaoshan.googleplayv2.base.BaseProtocol;
import com.xiaoshan.googleplayv2.bean.CategoryInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoshan on 2016/2/26.
 * 15:46
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfoBean>> {
    @Override
    protected String getInterfaceKey() {
        return "category";
    }

    @Override
    protected List<CategoryInfoBean> parseJson(String jsonString) {
        List<CategoryInfoBean> categoryInfoBeen = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonString);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String title = jsonObject.get("title").getAsString();
            CategoryInfoBean categoryInfoBean = new CategoryInfoBean();
            categoryInfoBean.title = title;
            categoryInfoBean.isTitleView = true;
            categoryInfoBeen.add(categoryInfoBean);
            JsonArray infos = jsonObject.getAsJsonArray("infos");
            for (JsonElement info : infos) {
                JsonObject object = info.getAsJsonObject();
                String name1 = object.get("name1").getAsString();
                String name2 = object.get("name2").getAsString();
                String name3 = object.get("name3").getAsString();
                String url1 = object.get("url1").getAsString();
                String url2 = object.get("url2").getAsString();
                String url3 = object.get("url3").getAsString();
                CategoryInfoBean infoBean = new CategoryInfoBean();
                infoBean.name1 = name1;
                infoBean.name2 = name2;
                infoBean.name3 = name3;
                infoBean.url1 = url1;
                infoBean.url2 = url2;
                infoBean.url3 = url3;
                categoryInfoBeen.add(infoBean);
            }
        }
        return categoryInfoBeen;
    }
}
