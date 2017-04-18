package com.ss.url.helper;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Saurav on 16-04-2017.
 */
public class AppHelperTest {


    @Test
    public void getRandomString() throws Exception {
        String random = AppHelper.getRandomString(10);
        assertNotNull(random);
    }

    @Test
    public void getURLBase() throws Exception {
        String baseUrl = "https://www.google.co.in/";
        String url = baseUrl + "?gfe_rd=cr&ei=wYDzWOLcBcX08we4l7foBQ#q=snapchat";
        String actual = AppHelper.getURLBase(url);
        assertTrue(actual.equals(baseUrl));

    }

    @Test
    public void getAccountIdAuthHeader() throws Exception {
        String auth = "Basic YWRtaW46YWRtaW4=";
        String actual = AppHelper.getAccountIdAuthHeader(auth);
        assertTrue(actual.equals("admin"));
    }

}