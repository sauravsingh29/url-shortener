package com.ss.url.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Saurav on 14-04-2017.
 */
@Getter
@AllArgsConstructor
@ToString
@Setter
public class URLDetails implements Serializable {
    private static final long serialVersionUID = -8490408770166444347L;

    private String url;

    private int redirectType;

    private String shortUrl;

    private int redirectCount;

    private String accountId;


}
