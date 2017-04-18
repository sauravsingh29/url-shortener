package com.ss.url.service;

import com.ss.url.entity.Account;
import com.ss.url.entity.URLDetails;
import com.ss.url.exception.UrlException;
import com.ss.url.repository.AccountRepository;
import com.ss.url.repository.URLRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Saurav on 16-04-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class URLServiceTest {

    private static final String baseUrl = "http://localhost:8080/register";
    private static final String url = "http://stackoverflow.com/questions/137212/how-to-solve-performance-problem-with-java-securerandom";

    @Mock
    private URLRepository urlRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private URLService urlService = new URLService(urlRepository, accountRepository);

    private Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account("admin", "admin");
    }

    @Test
    public void registerNewUrl() throws Exception {
        String actual = null;
        try {
            when(urlRepository.findURLDetailsByUrl(anyString())).thenReturn(null);
            when(accountRepository.findOne("Basic YWRtaW46YWRtaW4=")).thenReturn(account);
            actual = urlService.registerUrl(url, 302, "Basic YWRtaW46YWRtaW4=", baseUrl);
            assertNotNull(actual);
            verify(urlRepository).findURLDetailsByUrl(anyString());
            verify(accountRepository).findOne(anyString());
        } catch (UrlException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void registerUpdateUrl() throws Exception {
        Account account = new Account("admin", "admin");
        final URLDetails urlDetails = getUrlDetails();
        urlDetails.setAccount(account);

        String actual = null;
        try {
            when(urlRepository.findURLDetailsByUrl(anyString())).thenReturn(urlDetails);
            actual = urlService.registerUrl(url, 302, "Basic YWRtaW46YWRtaW4=", baseUrl);
            assertNotNull(actual);
            verify(urlRepository).findURLDetailsByUrl(anyString());
            verifyZeroInteractions(accountRepository);
        } catch (UrlException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = UrlException.class)
    public void registerUpdateUrlException() throws UrlException {
        final URLDetails urlDetails = getUrlDetails();
        urlDetails.setAccount(account);

        when(urlRepository.findURLDetailsByUrl(anyString())).thenThrow(new RuntimeException());
        urlService.registerUrl(url, 302, "Basic YWRtaW46YWRtaW4=", baseUrl);
    }

    private URLDetails getUrlDetails() {
        final URLDetails urlDetails = new URLDetails();
        urlDetails.setUrl(url);
        urlDetails.setRedirectCount(0);
        urlDetails.setRedirectType(302);
        urlDetails.setShortUrl("http://localhost:8080/xTtsfa");
        urlDetails.setAccount(account);
        return urlDetails;
    }

    @Test
    public void incrementVisitCount() throws UrlException {
        URLDetails urlDetails = getUrlDetails();
        when(urlRepository.findURLDetailsByUrl(anyString())).thenReturn(urlDetails);
        when(urlRepository.save(any(URLDetails.class))).thenReturn(urlDetails);
        urlService.incrementCounter("\"http://localhost:8080/xTtsfa\"");
        assertEquals(1, urlDetails.getRedirectCount());
        verify(urlRepository, times(1)).save(any(URLDetails.class));
    }

    @Test(expected = UrlException.class)
    public void incrementVisitCountException() throws UrlException {
        when(urlRepository.findURLDetailsByUrl(anyString())).thenReturn(null);
        urlService.incrementCounter("\"http://localhost:8080/xTtsfa\"");
        verify(urlRepository, never()).save(any(URLDetails.class));
    }

    @Test(expected = UrlException.class)
    public void incrementVisitCountRepoException() throws UrlException {
        when(urlRepository.findURLDetailsByUrl(anyString())).thenThrow(new RuntimeException());
        urlService.incrementCounter("\"http://localhost:8080/xTtsfa\"");
    }

    @Test
    public void getStatisticsByAccountId() throws UrlException {
        Object[] statisticsDetails = {url, 10};
        List<Object[]> statistics = new ArrayList<>(0);
        statistics.add(statisticsDetails);
        when(urlRepository.findURLDetailsByAccountId(anyString())).thenReturn(statistics);
        Map<String, Integer> actual = urlService.getStatByAccountId("admin");
        assertNotNull(actual);
        assertTrue(actual.entrySet().stream().map(stringIntegerEntry -> stringIntegerEntry.getValue()).anyMatch(integer -> integer == 10));
    }

}