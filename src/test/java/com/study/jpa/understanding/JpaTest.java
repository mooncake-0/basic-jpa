package com.study.jpa.understanding;

import com.study.jpa.domain.Member;
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

            Member member = new Member();
            member.setId(1L);
            member.setName("memberA"); // 비영속 상태

            System.out.println("BEF==============================");
            em.persist(member); // 영속화 상태 영컨에 의해 이 객체는 관리된다 (DB관리 대상이 아님)
            System.out.println("AFT==============================");

            Member member1 = em.find(Member.class, 1L);

            System.out.println("member1 = " + member1.getId());
            System.out.println("member1 = " + member1.getName());

            Member member2 = em.find(Member.class, 1L);

            System.out.println("result = " + (member1 == member2));

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

            Member member1 = new Member(2L, "member1");
            Member member2 = new Member(3L, "member2");

            entityManager.persist(member1);
            entityManager.persist(member2);

            System.out.println("==================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            entityManager.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("쓰기 지연 Test2 :: 조회")
    void test3() {
        tx.begin();

        try {

            Member member1 = entityManager.find(Member.class, 2L);
            member1.setName("hellllllo"); // 1차 캐시에 있는 애를 바꿔주게 됨.

            System.out.println("==================");

            tx.commit(); // 커밋할때는 1차 캐시 기반으로 변경을 감지함. (update 쿼리가 알아서 나감)
        } catch (Exception e) {
            tx.rollback();
        }finally {
            entityManager.close();
        }
        emf.close();
    }
}
