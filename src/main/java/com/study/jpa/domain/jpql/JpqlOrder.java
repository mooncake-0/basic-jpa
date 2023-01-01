package com.study.jpa.domain.jpql;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "jpql_order")
public class JpqlOrder {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private int orderAmount;

    @Embedded
    private JpqlAddress homeAddress;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private JpqlProduct product;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private JpqlMember member;
}
