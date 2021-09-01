package com.cos.blog.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private id;
}
