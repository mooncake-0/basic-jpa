package com.study.jpa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String city;
    private String street;
    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // FOR TEST
    public Member(String name){
        this.name = name;
    }

}
