package com.study.jpa.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    /*
    private String city;
    private String street;
    */

    @Embedded // 이건 생략해도 되긴 하는데, 해놓는걸 권장
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
}
