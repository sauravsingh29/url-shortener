package com.ss.url.service;

import com.ss.url.cache.URLCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Saurav on 16-04-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class URLServiceTest {

    @Mock
    private URLCache urlCache;

    @InjectMocks
    private URLService urlService = new URLService(urlCache);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void registerUrl() throws Exception {
        Mockito.doNothing().when(urlCache).addToCache(Matchers.anyString(), Matchers.anyObject());
        String url = urlService.registerUrl("http://ss.com/wid79sdhsdh?owdu=9", 301, "Basic YWRtaW46YWRtaW4=");
        assertNotNull(url);
    }
}