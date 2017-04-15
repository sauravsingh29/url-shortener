package com.ss.url.auth;

import com.ss.url.cache.AccountCache;
import com.ss.url.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by Saurav on 14-04-2017.
 */
@Service
public class BasicAuthService implements UserDetailsService {

    private AccountCache accountCache;

    @Autowired
    public BasicAuthService(AccountCache accountCache) {
        this.accountCache = accountCache;
    }

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        final Account account = accountCache.getFromCache(accountId);
        if (null != account) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
            return new User(accountId, account.getPassword(), Arrays.asList(grantedAuthority));
        }
        throw new UsernameNotFoundException(String.format("Account %s in not authorised to access service.", accountId));
    }
}
