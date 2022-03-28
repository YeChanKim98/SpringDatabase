package Hello.HelloSpring.repository;
import Hello.HelloSpring.domain.member;
import org.springframework.stereotype.Repository;

import java.util.*;

// MemoryRepository 인터페이스의 구현 : 들어온 id,name의 저장은 store와 sequnce가 맡는다

//@Repository // 스프링 컨테이너에 리포지토리로 등록해준다
public class MemoryMemberRepository implements MemberRepository{
    
    // 간단한 저장소라서 HashMap의 형태로 만들었지만, 동시성 문제로인해 상황에따라 다르게 사용해야한다 / 이하 왠만한 변수는 동시성을 고려해야한다
    //
    private static Map<Long, member> store = new HashMap<>();
    private static long sequence = 0L;
    
    @Override
    // 저장할 맴버객체를 받으면, Sequence에 1을 더한 후 내부 id로 저장하고, 해당 id와 mem를 Store에 키/벨류로 저장한다
    public member save(member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }

    @Override
    // 받은 id를 이용하여, store에서 name을 찾고 해당 name은 Null을 가질 수 있다 : Optional형 이기때문에 리턴또한 Optional로 감싸주어야한다
    public Optional<member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    // store의 값을 넘겨받은 name값과 비교하며, 같을경우 필터링으로 걸러서 리턴한다
    public Optional<member> findByName(String name) {
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
    }

    @Override
    // store에있는 모든 값을 ArrayList객체에 넣어서 반환
    public List<member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
