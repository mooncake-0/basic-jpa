package com.study.jpa.understanding;

import com.study.jpa.domain.sample.ExampleMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.*;

public class JpaTest {

    EntityManagerFactory emf;
    EntityManager entityManager;
    EntityTransaction tx;

    @BeforeEach
    void be() {
        emf = Persistence.createEntityManagerFactory("helloJpa");
        entityManager = emf.createEntityManager();
        tx = entityManager.getTransaction();
    }

    @Test
    @DisplayName("META-INF JPA TEST")
    void test1() {

        EntityManagerFactory helloJpa = Persistence.createEntityManagerFactory("helloJpa");
        EntityManager em = helloJpa.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            ExampleMember exampleMember = new ExampleMember();
            exampleMember.setId(1L);
            exampleMember.setName("memberA"); // 비영속 상태

            System.out.println("BEF==============================");
            em.persist(exampleMember); // 영속화 상태 영컨에 의해 이 객체는 관리된다 (DB관리 대상이 아님)
            System.out.println("AFT==============================");

            ExampleMember exampleMember1 = em.find(ExampleMember.class, 1L);

            System.out.println("member1 = " + exampleMember1.getId());
            System.out.println("member1 = " + exampleMember1.getName());

            ExampleMember exampleMember2 = em.find(ExampleMember.class, 1L);

            System.out.println("result = " + (exampleMember1 == exampleMember2));

            tx.commit(); // 영컨에 있는 애들이 DB에 등록됨 (INSERT Query 가 실제로 날라가는 곳)

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 내부적으로 DB를 물고 동작하고, 계속 팩토리로 뽑아내기 때문에 꼭 종료시켜야 함
        }

        helloJpa.close();
    }


    @Test
    @DisplayName("쓰기 지연 Test :: 저장")
    void test2() {

        tx.begin();

        try {

            ExampleMember exampleMember1 = new ExampleMember(2L, "member1");
            ExampleMember exampleMember2 = new ExampleMember(3L, "member2");

            entityManager.persist(exampleMember1);
            entityManager.persist(exampleMember2);

            System.out.println("==================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("쓰기 지연 Test2 :: 조회")
    void test3() {
        tx.begin();

        try {

            ExampleMember exampleMember1 = entityManager.find(ExampleMember.class, 2L);
            exampleMember1.setName("hellllllo"); // 1차 캐시에 있는 애를 바꿔주게 됨.

            System.out.println("==================");

            tx.commit(); // 커밋할때는 1차 캐시 기반으로 변경을 감지함. (update 쿼리가 알아서 나감)
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("Flush Test :: 쿼리 시점 확인")
    void test4() {

        tx.begin();

        try {

            ExampleMember exampleMemberA = new ExampleMember(100L, "MemberA");
            entityManager.persist(exampleMemberA);

            entityManager.flush();

            System.out.println("====================이 라인 이전에 FLUSH 실행 (SQL 나갈 것으로 예상)");

            tx.commit();

            System.out.println("====================트랜잭션 커밋 호출");

        } catch (Exception e) {
            tx.rollback();

        } finally {
            entityManager.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("준영속 상태 :: detach 후에는 쿼리가 나가지 않는다")
    void test5() {

        tx.begin();

        try {

            ExampleMember findExampleMember = entityManager.find(ExampleMember.class, 100L);
            findExampleMember.setName("Change Name");
            System.out.println("================ 변경사항이 발생: member.Name to [Change Name]");

            entityManager.detach(findExampleMember);
            System.out.println("================ Member Detach 발생: 영컨이 더이상 관리하지 않고, 1차 캐시에서 분리");

            tx.commit();
            System.out.println("================ Commit 호출: 1차 캐시에 없기 때문에 더티캐싱이 안됨. Update 쿼리 생성 안됨");

        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }
        emf.close();
    }


    @Test
    @DisplayName("준영속 상태 : EntityManager.clear()시 영컨과 1차 캐시를 비운다")
    void test6() {
        tx.begin();

        try {

            ExampleMember findA = entityManager.find(ExampleMember.class, 100L);

            entityManager.clear();
            System.out.println("=================== EM.clear() 발생: 영컨 및 1차 캐시가 비워진다");

            ExampleMember findB = entityManager.find(ExampleMember.class, 100L);
            System.out.println("=================== 다시 같은 객체 조회 발생: 1차 캐시에 100L의 Member가 없기 때문에 Select 쿼리 다시 발생");

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        }finally {
            entityManager.close();
        }

        emf.close();
    }
}
