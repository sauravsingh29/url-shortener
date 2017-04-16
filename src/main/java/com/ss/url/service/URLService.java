package com.ss.url.service;

import com.ss.url.cache.URLCache;
import com.ss.url.entity.URLDetails;
import com.ss.url.helper.AppHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.ss.url.helper.AppHelper.getRandomString;
import static com.ss.url.helper.AppHelper.getURLBase;

/**
 * Created by Saurav on 14-04-2017.
 */

@Service
public class URLService {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLService.class);

    private URLCache urlCache;

    @Autowired
    public URLService(URLCache urlCache) {
        this.urlCache = urlCache;
    }

    /**
     * Register requested url, after converting to shortened url with accountId and accountId pull from header.
     *
     * @param url           {@link String}
     * @param redirectType  {@link Integer}
     * @param authorization {@link String} base64 encoded string
     * @return shortened url {@link String}
     * @throws Exception
     */
    public String registerUrl(final String url, final int redirectType, final String authorization) throws Exception {
        LOGGER.debug("Register service invoked with params --> {}", new Object[]{url, redirectType, authorization});
        URLDetails urlDetails = urlCache.getFromCache(url);
        if (null != urlDetails)
            if (redirectType != urlDetails.getRedirectType()) {
                LOGGER.debug("Updating redirectType in existing entry");
                urlCache.updateRedirectType(url, urlDetails.getRedirectType());
                return urlDetails.getShortUrl();
            } else {
                LOGGER.warn("User trying to register, already registered record.");
                return urlDetails.getShortUrl();
            }
        LOGGER.debug("Registering new url details");
        String baseUrl = getURLBase(url);
        String shortenedUrl = baseUrl + getRandomString(6);
        urlCache.addToCache(url, new URLDetails(url, redirectType, shortenedUrl, 0, AppHelper.getAccountIdAuthHeader(authorization)));
        return shortenedUrl;
    }

    /**
     * Increase counter of visit when using shortened url.
     *
     * @param url
     */
    public void incrementCounter(final String url) {
        LOGGER.debug("Service invoked for incrementing redirect counter of params --> {}", url);
        int count = urlCache.getFromCache(url).getRedirectCount() + 1;
        urlCache.getFromCache(url).setRedirectCount(count);
    }

    /**
     * Generate stat of original url and their redirect count.
     *
     * @param accountId
     * @return
     */
    public Map<String, Integer> getStatByAccountId(final String accountId) {
        LOGGER.debug("Service invoked for getting redirect count with url using filter param --> {}", accountId);
        final Map<String, Integer> statMap = new HashMap<>(0);
        final Map<String, URLDetails> urlDetailsByAccountId = urlCache.getUrlDetailsByAccountId(accountId);
        for (URLDetails urlDetails : urlDetailsByAccountId.values())
            statMap.put(urlDetails.getUrl(), urlDetails.getRedirectCount());

        return statMap;
    }

    public URLDetails getUrlDetailsByShortUrl(final String shortUrl) {
        return urlCache.getUrlDetailsByShortUrl(shortUrl);
    }
}
