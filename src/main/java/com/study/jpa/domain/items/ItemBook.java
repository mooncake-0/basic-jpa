package com.study.jpa.domain.items;

import com.study.jpa.domain.Item;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@DiscriminatorValue(value = "book")
public class ItemBook extends Item {

    private String author;
    private String isbn;

}
