package com.codeup.springblog.models;

import javax.persistence.*;

@Entity
@Table(name="ad_images")
public class AdImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn (name = "ad_id")
    private Ad ad;
}
