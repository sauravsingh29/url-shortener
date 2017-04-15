package com.ss.url.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Saurav on 14-04-2017.
 */
@Getter
@Setter
@ToString
public class URLRequest implements Serializable {

    private static final long serialVersionUID = 6968109378467738154L;

    private String url;

    //Default to 302
    private int redirectType;

}
