package com.study.jpa.domain.items;

import com.study.jpa.domain.Item;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@DiscriminatorValue(value = "movie")
public class ItemMovie extends Item {

    private String director;
    private String actor;

}
