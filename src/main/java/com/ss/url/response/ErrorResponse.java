package com.ss.url.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Created by Saurav on 18-04-2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = -2517922805401530076L;
    private String type;

    @JsonProperty("error_messages")
    private String errorMessage;

    @JsonProperty("status")
    private HttpStatus httpStatus;
}
