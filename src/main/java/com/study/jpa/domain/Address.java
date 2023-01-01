package com.study.jpa.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

    private String city;
    private String street;

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    /*
     Setter 들은 함부로 선언되지 않도록 Private > 해당 Address 를 여러 군데에서 쓸 경우 같이 변경되기 때문이다 (같은 TX 내부일 경우)
     그냥 안만드는걸 권장
     */

    private void setCity(String city) {
        this.city = city;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    // 접근 시 getter 를 사용하지 않는 방법으로 생성하기도 함 >> Proxy 객체일 경우 에러가 발생
    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street);
    }*/

    // getter 를 사용해서 필드에 접근, Proxy 객체일 경우 프록시 초기화 진행
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street);
    }
}
