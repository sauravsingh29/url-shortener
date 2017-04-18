package com.ss.url.service;

import com.ss.url.UrlException;
import com.ss.url.entity.Account;
import com.ss.url.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Saurav on 16-04-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService = new AccountService(accountRepository);

    @Test
    public void createAccount() throws UrlException {
        when(accountRepository.findOne(anyString())).thenReturn(null);
        Account account = new Account("admin", "admin");
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        String actual = accountService.createAccount("admin");
        assertTrue(!actual.isEmpty());
        assertTrue(!actual.equals(account.getPassword()));
    }

    @Test
    public void createAccountExists() throws UrlException {
        Account account = new Account("admin", "admin");
        when(accountRepository.findOne(anyString())).thenReturn(account);
        String actual = accountService.createAccount("admin");
        assertNull(actual);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test(expected = UrlException.class)
    public void createAccountException() throws UrlException {
        when(accountRepository.findOne(anyString())).thenThrow(new RuntimeException());
        accountService.createAccount("admin");
    }
}