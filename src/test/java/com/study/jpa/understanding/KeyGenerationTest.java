package com.study.jpa.understanding;

import com.study.jpa.domain.sample.KeyTester;
import com.study.jpa.domain.sample.KeyTester2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class KeyGenerationTest {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloJpa");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

    @Test
    @DisplayName("Generate Type : IDENTITY")
    void test1() {
        System.out.println("IDENTITY Type 은 DB에게 전적으로 맡겨서 SQL INSERT 후에 부여받는다");

        tx.begin();

        try {

            KeyTester testerA = new KeyTester();
            testerA.setTestName("TesterA");

            System.out.println("1차 캐시 안에는 무조건 Key 에 PK 값이 매핑되어 있어야 합니다");
            System.out.println("PERSIST 호출 :: FLUSH 전에 SQL을 내보낸다");
            em.persist(testerA);

            System.out.println("COMMIT 호출 :: Commit 시점에 아무것도 보내지 않는다");
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("Generate Type : SEQ")
    void test2() {
        System.out.println("SEQ Type 은 DB 내 시퀀스를 생성하여 SQL 보다 빠른 통신을 지원한다");

        tx.begin();

        try {

            KeyTester testB = new KeyTester();
            testB.setTestName("TesterB");

            System.out.println("1차 캐시 PK 값이 필요하므로 Hibernate 통신으로 Seq를 조회하여 다음 값을 가지고 온다");
            em.persist(testB);

            System.out.println("Commit이 나가야 INSERT 문이 발생한다");
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("Generate Type : SEQ2")
    void test3() {
        System.out.println("SEQ Type 은 SEQUENCE Table 지정 없이는 통합 관리된다");

        tx.begin();

        try {

            KeyTester testA = new KeyTester();
            testA.setTestName("TESTER A");
            em.persist(testA);

            KeyTester2 testB = new KeyTester2();
            testB.setTestName("TESTER B");
            em.persist(testB);

            KeyTester testC = new KeyTester();
            testC.setTestName("TESTER C");
            em.persist(testC);

            System.out.println("testA.getId() = " + testA.getId());
            System.out.println("Other Table but PK in Sequence:: testB.getId() = " + testB.getId());
            System.out.println("testC.getId() = " + testC.getId());

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("Generate Type : SEQ3")
    void test4() {
        System.out.println("Entity 마다 독자적인 SEQ 관리를 위해선 Class에 명시해주면 된다");

        tx.begin();

        try {

            KeyTester testA = new KeyTester();
            testA.setTestName("TESTER A");
            em.persist(testA);

            KeyTester2 testB = new KeyTester2();
            testB.setTestName("TESTER B");
            em.persist(testB);

            KeyTester testC = new KeyTester();
            testC.setTestName("TESTER C");
            em.persist(testC);

            System.out.println("testA.getId() = " + testA.getId());
            System.out.println("Other Table but PK in Sequence:: testB.getId() = " + testB.getId());
            System.out.println("testC.getId() = " + testC.getId());

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("Generate Type : TABLE")
    void test5() {
        System.out.println("");

        tx.begin();

        try {

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
