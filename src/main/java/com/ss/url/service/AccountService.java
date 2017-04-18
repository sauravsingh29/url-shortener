package com.ss.url.service;

import com.ss.url.UrlException;
import com.ss.url.entity.Account;
import com.ss.url.repository.AccountRepository;
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

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Service to create account with password and store into cache
     *
     * @param accountId
     * @return
     * @throws UrlException
     */
    public String createAccount(final String accountId) throws UrlException {
        LOGGER.debug("Creating requested account, params --> {}", accountId);
        try {
            Account account = accountRepository.findOne(accountId);
            if (null != account) {
                return null;
            }
            account = new Account(accountId, getRandomString(8));
            accountRepository.save(account);
            return account.getPassword();
        } catch (Exception e) {
            throw new UrlException(e.getLocalizedMessage(), e);
        }
    }


}
