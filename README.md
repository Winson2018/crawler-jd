# crawler-jd
 爬取京东手机页面的数据

java
使用了HttpClient和Jsoup爬取数据，数据通过SpringData JPA保存到数据库

数据库初始化语句：

create database crawler;

CREATE TABLE `jd_item` (
                           `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                           `spu` bigint(15) DEFAULT NULL COMMENT '商品集合id',
                           `sku` bigint(15) DEFAULT NULL COMMENT '商品最小品类单元id',
                           `title` varchar(1000) DEFAULT NULL COMMENT '商品标题',
                           `price` float(10,0) DEFAULT NULL COMMENT '商品价格',
                           `pic` varchar(200) DEFAULT NULL COMMENT '商品图片',
                           `url` varchar(1500) DEFAULT NULL COMMENT '商品详情地址',
                           `created` datetime DEFAULT NULL COMMENT '创建时间',
                           `updated` datetime DEFAULT NULL COMMENT '更新时间',
                           PRIMARY KEY (`id`),
                           KEY `sku` (`sku`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8 COMMENT='京东商品';
