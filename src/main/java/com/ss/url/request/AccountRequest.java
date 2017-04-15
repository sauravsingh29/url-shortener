package com.ss.url.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Saurav on 14-04-2017.
 */
@Getter
@Setter
@ToString
public class AccountRequest {
    @JsonProperty("AccountId")
    private String accountId;
}
