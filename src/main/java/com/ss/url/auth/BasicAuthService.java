package com.ss.url.auth;

import com.ss.url.entity.Account;
import com.ss.url.repository.AccountRepository;
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

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        final Account account = accountRepository.findOne(accountId);
        if (null != account) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
            return new User(accountId, account.getPassword(), Arrays.asList(grantedAuthority));
        }
        throw new UsernameNotFoundException(String.format("Account %s in not authorised to access service.", accountId));
    }

}
