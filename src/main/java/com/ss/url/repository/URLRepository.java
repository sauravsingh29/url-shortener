package com.ss.url.repository;

import com.ss.url.UrlException;
import com.ss.url.entity.StatisticsDetails;
import com.ss.url.entity.URLDetails;
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
    @Query("SELECT URL.URL, URL.VISIT_COUNT FROM URL.REGISTRATION REG WHERE REG.ACC_ID = : accountId")
    List<StatisticsDetails> findURLDetailsByAccountId(@Param("accountId") final String accountId) throws UrlException;
}
