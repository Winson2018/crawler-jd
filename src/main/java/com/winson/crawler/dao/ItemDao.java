package com.winson.crawler.dao;

import com.winson.crawler.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：Winson
 * @date ：Created in 2021/7/25 15:30
 * @description：
 * @modified By：
 * @version: $
 */
public interface ItemDao extends JpaRepository<Item, Long> {

}
