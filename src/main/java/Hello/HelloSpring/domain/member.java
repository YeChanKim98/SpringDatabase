package Hello.HelloSpring.domain;

import javax.persistence.*;

@Entity
public class member {
    
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값생성 어노테이션사용 -> 옵션 : 자동증가 값 / IDENTITY : DB가 값을 생성해줌
    private Long id; // 시스템 내부에서 사용할 ID

    @Column(name="name")
    // 컬럼네임과 변수명이 일치하면 name파라미터를 명시하지 안아도 되지만, 다르면 명시해야함
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
