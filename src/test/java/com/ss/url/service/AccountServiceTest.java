package com.ss.url.service;

import com.ss.url.cache.AccountCache;
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
public class AccountServiceTest {

    @Mock
    private AccountCache accountCache;

    @InjectMocks
    private AccountService accountService = new AccountService(accountCache);

    @Test
    public void createAccount() throws Exception {
        Mockito.doNothing().when(accountCache).addToCache(Matchers.anyString(), Matchers.anyObject());
        assertTrue(accountService.createAccount("ss") != null);
    }

    @Test
    public void addToCache() throws Exception {
        Mockito.doNothing().when(accountCache).addToCache(Matchers.anyString(), Matchers.anyObject());
    }

}