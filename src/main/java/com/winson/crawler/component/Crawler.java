package com.winson.crawler.component;

import com.winson.crawler.entity.Item;
import com.winson.crawler.service.ItemService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author ：Winson
 * @date ：Created in 2021/7/25 15:42
 * @description：爬取京东商城的数据，保存到数据库
 * @modified By：
 * @version: $
 */

@Component
public class Crawler {
    private String baseUrl1 = "https://search.jd.com/Search?keyword=%E8%8B%B9%E6%9E%9C%E6%89%8B%E6%9C%BA" +
            "&qrst=1&wq=%E8%8B%B9%E6%9E%9C%E6%89%8B%E6%9C%BA" +
            "&ev=exbrand_Apple&pvid=d7bc9c2c418c4c019907c15c8d53d502" +
            "&s=57&click=0&page=";

    private String baseUrl2 = "https://movie.douban.com/tv/#!type=tv&tag=%E7%83%AD%E9%97%A8&sort=recommend&page_limit=20&page_start=0";

    @Autowired
    private ItemService itemService;

    public void doCrawler(){
        try {
            //使用httpclient发送请求，使用连接池创建
            CloseableHttpClient httpClient = HttpClientCreater.create();

            for (int i = 0; i < 10; i++) {
                //创建get请求
                HttpGet get = new HttpGet(baseUrl1 + (i * 2 + 1));
                get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
                //发送请求
                CloseableHttpResponse response = httpClient.execute(get);
                //取出响应的HTML
                String html = EntityUtils.toString(response.getEntity(), "utf-8");
                //使用jsoup解析HTML
                parseHtml(html);
                //parseHtmlDB(html);
                //需要进行翻页处理
                response.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void doCrawlerDB(){
        try {
            //使用httpclient发送请求，使用连接池创建
            CloseableHttpClient httpClient = HttpClientCreater.create();

            //for (int i = 0; i < 10; i++) {
            //创建get请求
            //HttpGet get = new HttpGet(baseUrl + (i * 2 + 1));
            HttpGet get = new HttpGet(baseUrl2);
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
            //发送请求
            CloseableHttpResponse response = httpClient.execute(get);
            //取出响应的HTML
            String html = EntityUtils.toString(response.getEntity(), "utf-8");
            //使用jsoup解析HTML
            parseHtml(html);
            //parseHtmlDB(html);
            //需要进行翻页处理
            response.close();
            //}
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 在方法中实现HTML的解析
     * 把商品数据保存到数据库
     *
     */
    private void parseHtml(String html){
        //使用Jsoup解析HTML，得到一个document对象
        Document document = Jsoup.parse(html);
        //将HTML中的商品列表解析出来
        Elements nodes = document.select("li.gl-item");
        for (Element node : nodes) {
            String sku = node.attr("data-sku");
            String spu = node.attr("data-spu");
            String title = node.select("div.p-name").text();
            String price = node.select("div.p-price i").text();
            //String picUrl = node.select("div.p-img img").attr("source-data-lazy-img");
            String picUrl = node.select("div.p-img img").attr("data-lazy-img");
            //下载图片，返回新文件名
            String picName = downloadImg(picUrl);
            //商品链接
            String itemUrl = node.select("div.p-img > a").attr("href");

            //把商品数据封装成一个item对象
            Item item = new Item();
            item.setSku(Long.parseLong(sku));
            if (!spu.equals("")){
                item.setSpu(Long.parseLong(spu));
            }
            item.setTitle(title);
            item.setPrice(Double.parseDouble(price));
            item.setPic(picName);
            item.setUrl(itemUrl);
            item.setCreated(new Date());
            item.setUpdated(new Date());
            //调用service保存到数据库
            itemService.save(item);
        }
    }

    /**
     * 在方法中实现HTML的解析
     * 把商品数据保存到数据库
     * 豆瓣
     */
    private void parseHtmlDB(String html){
        //使用Jsoup解析HTML，得到一个document对象
        Document document = Jsoup.parse(html);
        //将HTML中的商品列表解析出来
        Elements nodes = document.select("a.item");
        for (Element node : nodes) {
            //String sku = node.attr("data-sku");
            //String spu = node.attr("data-spu");
            String title = node.select("p").text();
            String price = node.select("p.strong").text();
            String picUrl = node.select("div.cover-wp img").attr("src");
            //下载图片，返回新文件名
            String picName = downloadImgDB(picUrl);
            //商品链接
            String itemUrl = node.attr("href");

            //把商品数据封装成一个item对象
            Item item = new Item();
            //item.setSku(Long.parseLong(sku));
            //item.setSpu(Long.parseLong(spu));
            item.setTitle(title);
            item.setPrice(Double.parseDouble(price));
            item.setPic(picName);
            item.setUrl(itemUrl);
            item.setCreated(new Date());
            item.setUpdated(new Date());
            //调用service保存到数据库
            itemService.save(item);
        }
    }


    /**
     * 图片下载
     * @param url
     * @return
     */
    private String downloadImg(String url){
        try {
            //创建一个httpclient对象
            CloseableHttpClient httpClient = HttpClientCreater.create();
            //将图片的URL封装成httpget对象
            HttpGet get = new HttpGet("http:" + url);
            //发送请求
            CloseableHttpResponse response = httpClient.execute(get);
            //取返回结果，保存到磁盘
            //使用UUID生成文件名
            //取文件扩展名
            String extName = url.substring(url.lastIndexOf("."));
            String fileName = UUID.randomUUID() + extName;
            //创建一个FileOutPutStream,把图片保存到磁盘
            FileOutputStream fileOutputStream = new FileOutputStream("E:\\project\\crawler-jd\\imgs\\"+ fileName);
            response.getEntity().writeTo(fileOutputStream);
            //关闭流
            fileOutputStream.close();
            response.close();

            //返回文件名
            return fileName;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 豆瓣图片下载
     * @param url
     * @return
     */
    private String downloadImgDB(String url){
        try {
            //创建一个httpclient对象
            CloseableHttpClient httpClient = HttpClientCreater.create();
            //将图片的URL封装成httpget对象
            HttpGet get = new HttpGet(url);
            //发送请求
            CloseableHttpResponse response = httpClient.execute(get);
            //取返回结果，保存到磁盘
            //使用UUID生成文件名
            //取文件扩展名
            String extName = url.substring(url.lastIndexOf("."));
            String fileName = UUID.randomUUID() + extName;
            //创建一个FileOutPutStream,把图片保存到磁盘
            FileOutputStream fileOutputStream = new FileOutputStream("E:\\project\\crawler-jd\\imgs"+ fileName);
            response.getEntity().writeTo(fileOutputStream);
            //关闭流
            fileOutputStream.close();
            response.close();

            //返回文件名
            return fileName;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
