package Hello.HelloSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Hello.HelloSpring.domain.member;

import java.util.Optional;


// 해당 인터페이스를 보고 구현체를 스프링이 알아서 만들어준다
public interface SpringDataJpaMemberRepository extends JpaRepository<member, Long>, MemberRepository{ // 데이터 엔티티와 해당 엔티티의 id타입
    @Override
    Optional<member> findById(Long aLong);
}
