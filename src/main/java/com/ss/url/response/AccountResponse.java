package com.ss.url.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;


/**
 * Created by Saurav on 14-04-2017.
 */
@Getter
@AllArgsConstructor
@ToString
public class AccountResponse implements Serializable {

    private static final long serialVersionUID = 723980590722131267L;

    private boolean success;
    private String description;
    private String password;

}
