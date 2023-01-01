package com.study.jpa.domain.jpql;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "jpql_team")
public class JpqlTeam {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<JpqlMember> members = new ArrayList<>();
}
