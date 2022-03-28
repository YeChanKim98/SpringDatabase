package Hello.HelloSpring.repository;
import Hello.HelloSpring.domain.member;
import java.util.List;
import java.util.Optional;

// 현재 맴버정보의 저장소를 선정하지 않은 상황이기에 Interface로 미리 구현 하여 제작 / 저장소 선정 후, 세부 구현 실시

public interface MemberRepository {
    member save(member member);

    // Null값에 대한 반환처리를 해주는 Optional사용
    Optional<member> findById(Long id);
    Optional<member> findByName(String name);
    List<member> findAll(); // 저장 된 모든 값 반환

}

