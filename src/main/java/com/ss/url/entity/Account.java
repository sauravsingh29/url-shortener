package com.ss.url.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Saurav on 14-04-2017.
 */

@Entity
@Table(name = "account", schema = "url")
@Getter
@Setter
@NoArgsConstructor
public class Account implements Serializable {

    private static final long serialVersionUID = 7486282651220621860L;

    @Column(name = "acc_id")
    @Id
    private String accountId;

    @Column(name = "acc_pwd")
    private String password;

    @OneToMany(mappedBy = "account", targetEntity = URLDetails.class)
    private List<URLDetails> urlDetails;

    public Account(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }
}