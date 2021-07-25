package com.winson.crawler.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：Winson
 * @date ：Created in 2021/7/25 15:23
 * @description：
 * @modified By：
 * @version: $
 */

@Entity
@Table(name="jd_item")
public class Item {
    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="spu")
    private Long spu;

    @Column(name="sku")
    private Long sku;

    @Column(name="title")
    private String title;

    @Column(name="price")
    private Double price;

    @Column(name="pic")
    private String pic;

    @Column(name="url")
    private String url;

    @Column(name="created")
    private Date created;

    @Column(name="updated")
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpu() {
        return spu;
    }

    public void setSpu(Long spu) {
        this.spu = spu;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
