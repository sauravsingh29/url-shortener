package com.ss.url.repository;

import com.ss.url.entity.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Saurav on 18-04-2017.
 */
public interface AccountRepository extends CrudRepository<Account, String> {
}
