package com.ss.url.controller;

import com.ss.url.request.URLRequest;
import com.ss.url.response.URLResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Saurav on 15-04-2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class URLControllerTest {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${local.server.port}")
    private int port;
    private String url;

    private static HttpHeaders getHeaders() {
        String plainCredentials = "admin:admin";
        String credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Before
    public void setUp() throws Exception {
        url = "http://localhost:" + port;
    }

    @Test
    public void registerUrl() throws Exception {
        URLRequest request = new URLRequest();
        request.setUrl("http://stackoverflow.com/questions/43425888/how-to-disable-spinner-click-setclickablefalse-not-working");
        HttpEntity<Object> entity = new HttpEntity<Object>(request, getHeaders());
        ResponseEntity<URLResponse> responseEntity = restTemplate.exchange(url + "/register", HttpMethod.POST, entity, URLResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test(expected = HttpClientErrorException.class)
    public void registerUrl_authFailed() throws Exception {
        URLRequest request = new URLRequest();
        request.setUrl("http://stackoverflow.com/questions/43425888/how-to-disable-spinner-click-setclickablefalse-not-working");
        HttpEntity<Object> entity = new HttpEntity<Object>(request);
        ResponseEntity<URLResponse> responseEntity = restTemplate.exchange(url + "/register", HttpMethod.POST, entity, URLResponse.class);
    }

    @Test
    public void accountStatics() throws Exception {
        HttpEntity<Object> entity = new HttpEntity<Object>(getHeaders());
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url + "/statistic/saurav", HttpMethod.POST, entity, Map.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}