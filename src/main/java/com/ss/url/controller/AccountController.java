package com.ss.url.controller;

import com.ss.url.request.AccountRequest;
import com.ss.url.response.AccountResponse;
import com.ss.url.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Saurav on 14-04-2017.
 */
@RestController
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping(value = "/account")
    public ResponseEntity<?> createAccount(@RequestBody(required = true) AccountRequest accountRequest) {
        if (null != accountRequest && !StringUtils.hasText(accountRequest.getAccountId()))
            return new ResponseEntity<String>("Missing AccountId from request body.", HttpStatus.BAD_REQUEST);
        LOGGER.debug("Incoming request to create account [{}]", accountRequest.toString());
        final String userPass = accountService.createAccount(accountRequest.getAccountId());
        AccountResponse response;
        if (null == userPass) {
            response = new AccountResponse(false, "AccountId already exists", null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        response = new AccountResponse(true, "Your account is open", userPass);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
