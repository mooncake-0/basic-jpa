package com.study.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE )
    @DiscriminatorColumn(name = "d_type")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    /*
     연관주가 아닌 카테고리
     */
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
