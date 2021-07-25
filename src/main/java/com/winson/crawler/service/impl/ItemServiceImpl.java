package com.winson.crawler.service.impl;

import com.winson.crawler.dao.ItemDao;
import com.winson.crawler.entity.Item;
import com.winson.crawler.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：Winson
 * @date ：Created in 2021/7/25 16:25
 * @description：
 * @modified By：
 * @version: $
 */

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;

    @Override
    @Transactional
    public void save(Item item) {
        itemDao.save(item);
    }
}
