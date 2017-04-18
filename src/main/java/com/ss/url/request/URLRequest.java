package com.ss.url.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by Saurav on 14-04-2017.
 */
@Getter
@Setter
@ToString
public class URLRequest implements Serializable {

    private static final long serialVersionUID = 6968109378467738154L;

    @NotBlank(message = "missing.url")
    private String url;

    //Default to 302
    @Length(max = 3, message = "redirect.length")
    private int redirectType;

}
