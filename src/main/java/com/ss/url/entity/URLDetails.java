package com.ss.url.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Saurav on 14-04-2017.
 */
@Entity
@Table(name = "registration", schema = "url")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class URLDetails implements Serializable {
    private static final long serialVersionUID = -8490408770166444347L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "redirect_type")
    private int redirectType;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "visit_count")
    private int redirectCount;

    @JoinColumn(name = "acc_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;


}
