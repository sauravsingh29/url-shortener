package com.ss.url.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiParam;
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
    @ApiParam(required = true)
    private String accountId;
}
