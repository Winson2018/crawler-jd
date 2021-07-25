package com.winson.crawler.controller;

import com.winson.crawler.component.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Winson
 * @date ：Created in 2021/7/25 16:44
 * @description：爬取京东商城数据入口
 * @modified By：
 * @version: $
 */

@RestController
public class CrawlerController {
    @Autowired
    private Crawler crawler;

    @RequestMapping("/crawler")
    public String doCrawler(String action){
        if ("start".equals(action)){
            //开启线程执行爬取过程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //京东
                    crawler.doCrawler();
                    //豆瓣
                    //crawler.doCrawlerDB();
                }
            }).start();
        }
        return "OK";
    }
}
