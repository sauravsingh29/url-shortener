package com.ss.url.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Saurav on 14-04-2017.
 */
@AllArgsConstructor
@Getter
@ToString
public class URLResponse implements Serializable {

    private static final long serialVersionUID = -6396591702382506842L;

    private String shortUrl;
}
