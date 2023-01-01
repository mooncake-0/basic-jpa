package com.study.jpa.domain.jpql;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "jpql_member")
public class JpqlMember {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private JpqlTeam team;

    @Override
    public String toString() {
        return "JpqlMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void changeTeam(JpqlTeam team) {

        team.getMembers().add(this);
        this.team = team;

    }
}
