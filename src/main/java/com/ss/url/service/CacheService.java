package com.ss.url.service;

import com.ss.url.cache.AccountCache;
import com.ss.url.cache.URLCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Saurav on 16-04-2017.
 */
@Service
public class CacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);

    private AccountCache accountCache;
    private URLCache urlCache;

    @Autowired
    public CacheService(AccountCache accountCache, URLCache urlCache) {
        this.accountCache = accountCache;
        this.urlCache = urlCache;
    }

    public String getAccountInfo(){
        return accountCache.openedAccount();
    }

    public String getRegisterInfo(){
        return urlCache.registerUrlInfo();
    }


}
