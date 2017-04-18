package com.ss.url.controller;

import com.ss.url.entity.URLDetails;
import com.ss.url.exception.UrlException;
import com.ss.url.helper.AppHelper;
import com.ss.url.request.URLRequest;
import com.ss.url.response.ErrorResponse;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static com.ss.url.Constants.*;
import static com.ss.url.helper.AppHelper.getAccountIdAuthHeader;

/**
 * Created by Saurav on 14-04-2017.
 */
@RestController
public class URLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLController.class);

    private URLService urlService;

    @Autowired
    public URLController(URLService registerService) {
        this.urlService = registerService;
    }

    @ApiOperation(value = "Register url to short url", notes = "Requires a ulr string for registration purpose", response = URLResponse.class, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> registerUrl(@Valid @RequestBody URLRequest urlRequest, @RequestHeader(value = "Authorization", required = true) String authorization, HttpServletRequest servletRequest) {
        LOGGER.debug("Register process invoked with params :: {}", urlRequest.toString());
        String url = urlRequest.getUrl();
        int requestType = Integer.valueOf(urlRequest.getRedirectType());
        if (requestType == 0) {
            requestType = 302;
        }
        try {
            String response = urlService.registerUrl(url, requestType, authorization, servletRequest.getRequestURL().toString());
            return new ResponseEntity<>(new URLResponse(response), HttpStatus.OK);
        } catch (UrlException e) {
            return logAndReturnErrorResponseEntity(e);
        }
    }

    @ApiOperation(value = "stat based on accountId", notes = "Requires accountId from path parameter to get statistics of url", response = Map.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/statistic/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> accountStatics(@PathVariable(value = "accountId", required = true) String accountId, @RequestHeader(value = "Authorization", required = true) String authorization) {
        try {
            String authAccId = getAccountIdAuthHeader(authorization);
            if (authAccId.equalsIgnoreCase(accountId)) {
                return new ResponseEntity<>(urlService.getStatByAccountId(accountId), HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(AUTHENTICATION, String.format(UNAUTHORIZED, authAccId), HttpStatus.UNAUTHORIZED);
                return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
            }
        } catch (UrlException e) {
            return logAndReturnErrorResponseEntity(e);
        }
    }

    @GetMapping(value = "/{shortUrl}")
    public ResponseEntity<?> redirect(@PathVariable("shortUrl") String shortUrl, HttpServletRequest servletRequest) {
        LOGGER.debug("Redirecting for shortened url {}", shortUrl);
        try {
            String tinyUrl = servletRequest.getRequestURL().toString();
            tinyUrl = AppHelper.getURLBase(tinyUrl) + shortUrl;
            LOGGER.debug("Trying to find url details for shortened url {}", tinyUrl);
            final URLDetails urlDetails = urlService.getUrlDetailsByShortUrl(tinyUrl);
            if (null != urlDetails) {
                urlService.incrementCounter(urlDetails.getUrl());
                URI location = new URI(urlDetails.getUrl());
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setLocation(location);
                return new ResponseEntity<>(httpHeaders, urlDetails.getRedirectType() == 302 ? HttpStatus.FOUND : HttpStatus.MOVED_PERMANENTLY);
            }
        } catch (UrlException e) {
            return logAndReturnErrorResponseEntity(e);
        } catch (URISyntaxException e) {
            LOGGER.error("Exception :: {}", e);
            ErrorResponse errorResponse = new ErrorResponse(INTERNAL, INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
        } catch (MalformedURLException e) {
            LOGGER.error("Exception :: {}", e);
            ErrorResponse errorResponse = new ErrorResponse(INTERNAL, INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> logAndReturnErrorResponseEntity(UrlException e) {
        LOGGER.error("Exception :: {}", e);
        ErrorResponse errorResponse = new ErrorResponse(INTERNAL, INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }


}
