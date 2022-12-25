package com.study.jpa.understanding;

import com.study.jpa.domain.Member;
import com.study.jpa.domain.Team;
import com.study.jpa.domain.sample.ExampleMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class RelationOwnerTest {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloJpa");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();


    @DisplayName("연관관계 주인 : 주의 사항")
    @Test
    void test1() {

        System.out.println("getMembers()로 멤버만 꺼내서 .add()만 수행하면 연관관계가 매핑되지 않는다");

        tx.begin();

        try {

            Member memberA = new Member();
            memberA.setName("memberA");
            em.persist(memberA);

            Team team = new Team();
            team.setName("teamA");
            team.getMembers().add(memberA);
            System.out.println("members List는 활용 (조회용) 으로 쓰이는 객체이지 DB UPDATE 와는 아무 관계 없다고 보는게 더 편하다 (읽기 전용)");
            System.out.println("연관관계의 주인은 Member 객체이다. 따라서 memberA.setTeam()을 해줘야 함");

//            memberA.setTeam(team);
            em.persist(team);

            em.flush();
            em.clear();

            tx.commit();

        } catch (Exception e) {
            tx.commit();
        } finally {
            em.close();
        }

        emf.close();
    }

}
