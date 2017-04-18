package com.ss.url.controller;

import com.ss.url.UrlException;
import com.ss.url.request.AccountRequest;
import com.ss.url.response.AccountResponse;
import com.ss.url.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.ss.url.Constants.ACC_CREATED;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Saurav on 18-04-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController = new AccountController(accountService);

    private AccountRequest accountRequest;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        accountRequest = new AccountRequest();
        accountRequest.setAccountId("saurav");
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testCreateAccount() throws UrlException, Exception {
        AccountResponse response = new AccountResponse(true, ACC_CREATED, "xRsggt");
        when(accountService.createAccount(anyString())).thenReturn("xRsggt");

        this.mockMvc.perform(post("/account").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{ \"AccountId\" : \"Saurav\"}")).andExpect(status().isOk());
    }


    @Test
    public void testCreateAccountBadRequest() throws UrlException, Exception {
        AccountResponse response = new AccountResponse(true, ACC_CREATED, "xRsggt");
        when(accountService.createAccount(anyString())).thenReturn("xRsggt");

        this.mockMvc.perform(post("/account").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{ \"AccountId\" : \"\"}")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateAccountExists() throws UrlException, Exception {
        AccountResponse response = new AccountResponse(true, ACC_CREATED, "xRsggt");
        when(accountService.createAccount(anyString())).thenReturn(null);

        this.mockMvc.perform(post("/account").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{ \"AccountId\" : \"Saurav\"}")).andExpect(status().isConflict());
    }

    @Test
    public void testCreateAccountException() throws UrlException, Exception {
        AccountResponse response = new AccountResponse(true, ACC_CREATED, "xRsggt");
        when(accountService.createAccount(anyString())).thenThrow(new UrlException("Error getting accountId", null));

        this.mockMvc.perform(post("/account").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content("{ \"AccountId\" : \"Saurav\"}")).andExpect(status().isInternalServerError());
    }
}
