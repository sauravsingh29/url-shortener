package com.ss.url.controller;

import com.ss.url.entity.URLDetails;
import com.ss.url.request.URLRequest;
import com.ss.url.response.URLResponse;
import com.ss.url.service.URLService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by Saurav on 14-04-2017.
 */
@RestController
public class URLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private URLService urlService;

    @Autowired
    public URLController(URLService registerService) {
        this.urlService = registerService;
    }

    @ApiOperation(value = "Register url to short url", notes = "Requires a ulr string for registration purpose", response = URLResponse.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUrl(@RequestBody URLRequest urlRequest, @RequestHeader(value = "Authorization", required = true) String authorization) {
        LOGGER.debug("Register process invoked with params :: {}", urlRequest.toString());
        if (null != urlRequest && !StringUtils.hasText(urlRequest.getUrl()))
            return new ResponseEntity<>("Missing mandatory url that needs shortening", HttpStatus.BAD_REQUEST);

        String url = urlRequest.getUrl();
        int requestType = urlRequest.getRedirectType();
        if (requestType == 0)
            requestType = 302;

        try {
            String response = urlService.registerUrl(url, requestType, authorization);
            return new ResponseEntity<>(new URLResponse(response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "stat based on accountId", notes = "Requires accountId from path parameter to get statistics of url", response = Map.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/statistic/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> accountStatics(@PathVariable(value = "accountId", required = true) String accountId) {
        return new ResponseEntity<>(urlService.getStatByAccountId(accountId), HttpStatus.OK);
    }

    @GetMapping(value = "/redirect/{shortUrl}")
    public ResponseEntity<?> redirect(@PathVariable("shortUrl") String shortUrl) {
        LOGGER.debug("Redirecting for shortened url {}", shortUrl);
        final URLDetails urlDetails = urlService.getUrlDetailsByShortUrl(shortUrl);
        if (null != urlDetails) {
            urlService.incrementCounter(urlDetails.getUrl());
            URI location = null;
            try {
                location = new URI(urlDetails.getUrl());
            } catch (URISyntaxException e) {
                return new ResponseEntity<>("Invalid origin url found against shortUrl", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);
            return new ResponseEntity<>(httpHeaders, urlDetails.getRedirectCount() == 302 ? HttpStatus.FOUND : HttpStatus.MOVED_PERMANENTLY);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
    }


}
