package com.ss.url.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Saurav on 14-04-2017.
 */

@AllArgsConstructor
public class Account implements Serializable {

    private static final long serialVersionUID = 7486282651220621860L;

    private String accountId;

    @Getter
    private String password;

}