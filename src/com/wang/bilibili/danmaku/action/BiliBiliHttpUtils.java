package com.wang.bilibili.danmaku.action;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BiliBiliHttpUtils {

    public static String getHistory(String rootId) {
        URI uri = null;
        try {
            // https://api.live.bilibili.com/xlive/web-room/v1/dM/gethistory?roomid=8223873
            uri = new URIBuilder().setScheme("http").setHost("api.live.bilibili.com").setPort(80).setPath("/xlive/web-room/v1/dM/gethistory").setParameter("roomid", rootId).build();
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(uri);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true).build();
        httpGet.setConfig(requestConfig);
        String historyDanMaKu = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                historyDanMaKu = EntityUtils.toString(httpEntity);
            }
        } catch (IOException clientProtocolException) {
            clientProtocolException.printStackTrace();
        }
        return historyDanMaKu;
    }

}
