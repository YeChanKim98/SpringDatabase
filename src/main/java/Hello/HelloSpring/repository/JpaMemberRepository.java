package Hello.HelloSpring.repository;

import Hello.HelloSpring.domain.member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // 스프링 부트가 빌드할, 때 프로퍼티와 의존성을 확인하고 DB와 연결하여 EntityManager객체를 생성해 놓는다. 해당 객체를 받아서 쓰기만 하면 된다
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public member save(member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<member> findById(Long id) {
        member member = em.find(member.class,id); // 인자로 어떠한 엔티티를 찾을것인지, PK 총 2개의 인자를 넘겨서 select를 실행한다
        return Optional.ofNullable(member); // 널 값이 있을 수 있으니
    }
    
 
    // PK 기반이 아닌 쿼리는 JPQL을 작성하여 실행해야한다 -> 하지만 이것을 해결한 것이 Spring Data JPA이다. 쿼리문의 작성을 필요로 하지 않는다.
    @Override
    public Optional<member> findByName(String name) {
        return em.createQuery("select m from member m where m.name = :name",member.class).setParameter("name",name).getResultList().stream().findAny();
    }

    @Override
    public List<member> findAll() {
        return em.createQuery("select m from member m",member.class).getResultList(); // JPQL을 이용
        // 인라인 단축키 : Ctrl + Alt + N
    }
}
