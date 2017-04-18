package com.ss.url.controller;

import com.ss.url.exception.UrlException;
import com.ss.url.request.AccountRequest;
import com.ss.url.response.AccountResponse;
import com.ss.url.response.ErrorResponse;
import com.ss.url.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ss.url.Constants.*;

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


    @ApiOperation(value = "Create Account", notes = "Requires AccountId to create account", response = AccountResponse.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> createAccount(@Valid @RequestBody(required = true) AccountRequest accountRequest) {
        LOGGER.debug("Incoming request to create account [{}]", accountRequest.toString());
        final String userPass;
        try {
            userPass = accountService.createAccount(accountRequest.getAccountId());
            AccountResponse response;
            if (null == userPass) {
                response = new AccountResponse(false, ACC_EXISTS, null);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            response = new AccountResponse(true, ACC_CREATED, userPass);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UrlException e) {
            return new ResponseEntity<>(new ErrorResponse(INTERNAL, INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
