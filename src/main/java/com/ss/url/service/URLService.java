package com.ss.url.service;

import com.ss.url.entity.URLDetails;
import com.ss.url.exception.UrlException;
import com.ss.url.repository.AccountRepository;
import com.ss.url.repository.URLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ss.url.helper.AppHelper.*;

/**
 * Created by Saurav on 14-04-2017.
 */

@Service
public class URLService {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLService.class);

    private URLRepository urlRepository;
    private AccountRepository accountRepository;


    @Autowired
    public URLService(URLRepository urlRepository, AccountRepository accountRepository) {
        this.urlRepository = urlRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Register requested url, after converting to shortened url with accountId and accountId pull from header.
     *
     * @param url           {@link String}
     * @param redirectType  {@link Integer}
     * @param authorization {@link String} base64 encoded string
     * @param baseUrl       {@link String} base url to register shortened url
     * @return shortened url {@link String}
     * @throws UrlException
     */
    public String registerUrl(final String url, final int redirectType, final String authorization, final String baseUrl) throws UrlException {
        LOGGER.debug("Register service invoked with params --> {}", url, redirectType, authorization);
        String shortenedUrl;
        try {
            URLDetails urlDetails = urlRepository.findURLDetailsByUrl(url);
            if (null != urlDetails) {
                if (redirectType != urlDetails.getRedirectType()) {
                    LOGGER.debug("Updating redirectType in existing entry");
                    urlDetails.setRedirectType(redirectType);
                    urlRepository.save(urlDetails);
                    shortenedUrl = urlDetails.getShortUrl();

                } else {
                    LOGGER.warn("User trying to register, already registered record.");
                    shortenedUrl = urlDetails.getShortUrl();
                }
            } else {
                LOGGER.debug("Registering new url details");

                shortenedUrl = getURLBase(baseUrl) + getRandomString(6);

                urlDetails = new URLDetails();
                urlDetails.setUrl(url);
                urlDetails.setRedirectType(redirectType);
                urlDetails.setShortUrl(shortenedUrl);
                urlDetails.setRedirectCount(0);
                urlDetails.setAccount(accountRepository.findOne(getAccountIdAuthHeader(authorization)));
                urlRepository.save(urlDetails);
            }
        } catch (Exception e) {
            throw new UrlException(e.getLocalizedMessage(), e);
        }
        return shortenedUrl;
    }

    /**
     * Increase counter of visit when using shortened url.
     *
     * @param url
     */
    public void incrementCounter(final String url) throws UrlException {
        LOGGER.debug("Service invoked for incrementing redirect counter of params --> {}", url);
        URLDetails urlDetails = null;
        try {
            urlDetails = urlRepository.findURLDetailsByUrl(url);
        } catch (Exception e) {
            throw new UrlException(e.getLocalizedMessage(), e);
        }
        if (null != urlDetails) {
            urlDetails.setRedirectCount(urlDetails.getRedirectCount() + 1);
            urlRepository.save(urlDetails);
        } else {
            throw new UrlException(String.format("Url %s is not registered with us", url), null);
        }
    }

    /**
     * Generate stat of original url and their redirect count.
     *
     * @param accountId
     * @return
     */
    public Map<String, Integer> getStatByAccountId(final String accountId) throws UrlException {
        LOGGER.debug("Service invoked for getting redirect stat with url using filter param --> {}", accountId);
        final Map<String, Integer> statMap = new HashMap<>(0);
        List<Object[]> statisticsDetails = null;
        try {
            statisticsDetails = urlRepository.findURLDetailsByAccountId(accountId);
        } catch (Exception e) {
            throw new UrlException(e.getLocalizedMessage(), e);
        }
        statisticsDetails.forEach(sd -> statMap.put((String) sd[0], Integer.valueOf(String.valueOf(sd[1]))));
        return statMap;
    }

    /**
     * Find URLDetails {@link URLDetails} by url.
     *
     * @param shortUrl {@link String} by shortened url
     * @return URLDetails
     * @throws UrlException
     */
    public URLDetails getUrlDetailsByShortUrl(final String shortUrl) throws UrlException {
        return urlRepository.findURLDetailsByShortUrl(shortUrl);
    }
}
