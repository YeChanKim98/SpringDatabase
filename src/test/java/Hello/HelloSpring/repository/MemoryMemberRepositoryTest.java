package Hello.HelloSpring.repository;
import Hello.HelloSpring.domain.member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

// 테스트의 메서드 실행순서는 보장되지 않으므로, 타 메소드가 먼저 실행된다는 조건을 고려해서는 안 된다
// 모든 메소드가 중복이름을 고려할 수 없으므로, 테스트 종료마다 초기화를 해주어야함

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 각 메서드가 끝날때마다 실행되는 콜백 메소드
    public void afterEach(){
        repository.clearStore();
    }

    @Test // Junit 을 사용
    public void save(){
        member member = new member();
        member.setName("Test_Set_Name");
        repository.save(member);

        // 반환형이 Optional 이기에 get 으로 값을 따로 빼주어야 한다 : 원래는 get 으로 바로 빼는 것은 아니지만 테스트이기 때문에 그냥 진행
        member result = repository.findById(member.getId()).get();

        // Junit 혹은 Assertj 에서 지원하는 값 비교 메소드로, 기대값과 실제값을 입력하여 실행한다
        // 성공시 반응이 없으며, 실패시 오류메시지로, 기대값과 실제값을 출력해준다. 해당 출력을 보고 디버깅을 할 수 있다
        // 요즘은 Junit 이 아닌 Assertj 에서 제공하는 Assertion 을 더 애용한다

        Assertions.assertEquals(member,result); // Jnuit
        org.assertj.core.api.Assertions.assertThat(member).isEqualTo(result); // Assertj : 비교 순서를 헷갈릴 일이 없음

        System.out.println("Find By ID Test : Success!");
    }

    @Test
    public void findByName(){
        member member1 = new member();
        member1.setName("Member_1");
        repository.save(member1);

        member member2 = new member();
        member2.setName("Member_2");
        repository.save(member2);

        member result = repository.findByName("Member_1").get();
        Assertions.assertEquals(member1,result);

        result = repository.findByName("Member_2").get();
        Assertions.assertEquals(member2,result);

        System.out.println("Find By Name Test : Success!");
    }

    @Test
    public void findAll(){
        member member3 = new member();
        member3.setName("Member_3");
        repository.save(member3);

        member member4 = new member();
        member4.setName("Member_4");
        repository.save(member4);

        List<member> result = repository.findAll();
        Assertions.assertEquals(2,result.size());

        System.out.println("Find All Test : Success!");
    }


}
