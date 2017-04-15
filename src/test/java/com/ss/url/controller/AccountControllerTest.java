package com.ss.url.controller;

import com.ss.url.request.AccountRequest;
import com.ss.url.response.AccountResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Saurav on 15-04-2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AccountControllerTest {

    @Value("${local.server.port}")
    private int port;

    private String url;

    @Before
    public void setUp() throws Exception {
        url = "http://localhost:" + port + "/account";
    }

    @Test
    public void createAccount() throws Exception {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId("Saurav");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> entity = new HttpEntity<Object>(accountRequest);
        ResponseEntity<AccountResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, AccountResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}