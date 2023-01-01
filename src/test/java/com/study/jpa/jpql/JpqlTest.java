package com.study.jpa.jpql;

import com.study.jpa.domain.jpql.JpqlMember;
import com.study.jpa.domain.jpql.JpqlTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.*;
import java.util.List;

public class JpqlTest {


    EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloJpa");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    @Test
    @DisplayName("JPQL Paging Test")
    void test1() {
        tx.begin();
        try {

            for (int i = 0; i < 100; i++) {
                JpqlMember member1 = new JpqlMember();
                member1.setName("Member" + i);
                member1.setAge(i + 10);
                em.persist(member1);
            }

            em.flush();
            em.clear();

            List<JpqlMember> resultList = em.createQuery("select m from JpqlMember m order by m.age desc", JpqlMember.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("resultList.size() = " + resultList.size());
            for (JpqlMember m :
                    resultList) {
                System.out.println("JpqlMember = " + m);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("JPQL Join Test")
    void test() {
        tx.begin();
        try {

            JpqlTeam team1 = new JpqlTeam();
            team1.setName("Team 1");
            em.persist(team1);

            JpqlMember member1 = new JpqlMember();
            member1.setName("Member 1");
            member1.setAge(10);
            member1.changeTeam(team1);

            em.persist(member1);

            em.flush();
            em.clear();

            String query = "select m from JpqlMember m inner join m.team t"; // 내부 조인
//            String query = "select m from JpqlMember m left join m.team t"; // 외부 조인, left join // 같은 값이 없어도 member 를 살림

            List<JpqlMember> resultList = em.createQuery(query, JpqlMember.class)
                    .getResultList();

            for (JpqlMember member :
                    resultList) {
                System.out.println("member = " + member);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("JPQL Join : 관계없는 엔티티도 외부 조인 가능")
    void testd() {
        tx.begin();
        try {

            JpqlTeam team1 = new JpqlTeam();
            team1.setName("Team 1");
            em.persist(team1);

            JpqlMember member1 = new JpqlMember();
            member1.setName("Member 1");
            member1.setAge(10);
            member1.changeTeam(team1);

            em.persist(member1);

            em.flush();
            em.clear();

            String query = "select m from JpqlMember m left join Team t on m.name = t.name";
            List<JpqlMember> resultList1 = em.createQuery(query, JpqlMember.class)
                    .getResultList();

            for (JpqlMember m :
                    resultList1) {
                System.out.println("m = " + m);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("")
    void test3() {
        tx.begin();
        try {

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("")
    void test4() {
        tx.begin();
        try {

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("")
    void test5() {
        tx.begin();
        try {

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
