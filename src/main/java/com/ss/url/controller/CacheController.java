package com.ss.url.controller;

import com.ss.url.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Saurav on 16-04-2017.
 */
@RestController
public class CacheController {

    private CacheService cacheService;

    @Autowired
    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping(value = "/account")
    public String getAccountInfo(){
        return cacheService.getAccountInfo();
    }

    @GetMapping(value = "/register")
    public String getRegisterInfo(){
        return cacheService.getRegisterInfo();
    }
}
