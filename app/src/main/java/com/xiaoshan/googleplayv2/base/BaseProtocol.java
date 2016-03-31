package com.xiaoshan.googleplayv2.base;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiaoshan.googleplayv2.config.Constants;
import com.xiaoshan.googleplayv2.utils.FileUtils;
import com.xiaoshan.googleplayv2.utils.IOUtils;
import com.xiaoshan.googleplayv2.utils.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by xiaoshan on 2016/2/22.
 * 21:08
 */
public abstract class BaseProtocol<T> {

    public T loadData(int index) throws Exception {

        String jsonString;
        File cacheFile = getCacheFile(index);
        if (cacheFile != null && cacheFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(cacheFile));
            String timeStamp = reader.readLine();
            if ((System.currentTimeMillis() - Long.valueOf(timeStamp)) < Constants.VALIDATE_TIME) {
                LogUtils.sf("从本地取缓存");
                jsonString = reader.readLine();
            } else {
                jsonString = getDataFromNet(index);
            }
        } else {
            jsonString = getDataFromNet(index);
        }

        return parseJson(jsonString);
    }

    private String getDataFromNet(int index) throws IOException, HttpException {
        String jsonString;
        HttpUtils httpUtils = new HttpUtils();
        String url = Constants.URLS.BASE_URL + getInterfaceKey();
        Map<String, String> extraParams = getExtraParams();
        RequestParams params = new RequestParams();
        if (extraParams == null) {
            params.addQueryStringParameter("index", index + "");
        } else {
            for (Map.Entry<String, String> arg : extraParams.entrySet()) {
                String key = arg.getKey();
                String value = arg.getValue();
                params.addQueryStringParameter(key,value);
            }
        }

        jsonString = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, params).readString();
        saveAsCache(index, jsonString);
        return jsonString;
    }

    protected Map<String, String> getExtraParams() {
        return null;
    }

    private void saveAsCache(int index, String jsonString) {
        BufferedWriter writer = null;
        try {
            File cacheFile = getCacheFile(index);
            if (cacheFile != null) {
                writer = new BufferedWriter(new FileWriter(cacheFile));
                writer.write(System.currentTimeMillis() + "");
                writer.newLine();
                writer.write(jsonString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }

    private File getCacheFile(int index) {
        Map<String, String> extraParams = getExtraParams();
        String name;
        if (extraParams == null) {
            return new File(FileUtils.getDir("json"), getInterfaceKey() + "." + index);
        } else {
            for (Map.Entry<String, String> extraParam : extraParams.entrySet()) {
                String key = extraParam.getKey();
                String value = extraParam.getValue();
                if (key.equals("packageName")) {
                    name = getInterfaceKey() + "." + value;
                    return new File(FileUtils.getDir("json"), name);
                }
            }
        }

        return null;
    }

    protected abstract String getInterfaceKey();

    protected abstract T parseJson(String jsonString);
}
