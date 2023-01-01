package com.study.jpa.domain.sample;

import lombok.Data;

import javax.persistence.*;

@Data
@SequenceGenerator(
        name = "KEY_TESTER_SEQ_GENERATOR",
        sequenceName = "KEY_TESTER_SEQUENCE",
        initialValue = 1, allocationSize = 50
)
public class KeyTester {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "KEY_TESTER_SEQ_GENERATOR")
    private Long id;

    private String testName;

}
