package com.winson.crawler.component;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：Winson
 * @date ：Created in 2021/7/25 15:45
 * @description：
 * @modified By：
 * @version: $
 */
public class HttpClientCreater {
    //创建一个http连接池
    private static PoolingHttpClientConnectionManager connectionManager;

    public static CloseableHttpClient create(){
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        return httpClient;
    }
}
