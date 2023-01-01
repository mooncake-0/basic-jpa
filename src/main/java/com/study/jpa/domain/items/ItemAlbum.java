package com.study.jpa.domain.items;

import com.study.jpa.domain.Item;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@DiscriminatorValue(value = "album")
public class ItemAlbum extends Item {

    private String artist;
    private String etc;
}
