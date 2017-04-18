package com.ss.url.repository;

import com.ss.url.entity.URLDetails;
import com.ss.url.exception.UrlException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Saurav on 18-04-2017.
 */
@Lazy
public interface URLRepository extends CrudRepository<URLDetails, Integer> {

    /**
     * Find URLDetails {@link URLDetails} by url.
     *
     * @param url
     * @return
     * @throws UrlException
     */
    URLDetails findURLDetailsByUrl(final String url) throws UrlException;

    /**
     * Find all statistical details which consists of url and their visit count
     *
     * @param accountId
     * @return
     * @throws UrlException
     */
    @Query(value = "SELECT REG.URL, REG.VISIT_COUNT FROM URL.REGISTRATION REG WHERE REG.ACC_ID = :accountId", nativeQuery = true)
    List<Object[]> findURLDetailsByAccountId(@Param("accountId") final String accountId) throws UrlException;

    /**
     * Find URLDetails {@link URLDetails} by url.
     *
     * @param shortUrl {@link String} by tiny url
     * @return URLDetails
     * @throws UrlException
     */
    URLDetails findURLDetailsByShortUrl(final String shortUrl) throws UrlException;
}