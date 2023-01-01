package com.study.jpa.domain.jpql;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "jpql_product")
public class JpqlProduct {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private int price;

    private int stockAmount;
}
