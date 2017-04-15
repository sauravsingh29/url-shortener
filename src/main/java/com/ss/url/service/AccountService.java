package com.ss.url.service;

import com.ss.url.cache.AccountCache;
import com.ss.url.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ss.url.helper.AppHelper.getRandomString;

/**
 * Created by Saurav on 14-04-2017.
 */
@Service
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private AccountCache accountCache;

    @Autowired
    public AccountService(AccountCache accountCache) {
        this.accountCache = accountCache;
    }

    /**
     * Service to create account with password and store into cache
     *
     * @param accountId
     * @return
     */
    public String createAccount(final String accountId) {
        LOGGER.debug("Creating requested account, params --> {}", accountId);
        if (accountCache.contains(accountId))
            return null;
        final Account account = new Account(accountId, getRandomString(8));
        accountCache.addToCache(accountId, account);
        return account.getPassword();
    }


}
