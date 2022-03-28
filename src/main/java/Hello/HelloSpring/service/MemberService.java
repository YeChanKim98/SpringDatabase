package Hello.HelloSpring.service;
import Hello.HelloSpring.domain.member;
import Hello.HelloSpring.repository.MemberRepository;
import Hello.HelloSpring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service // 컨테이너에 서비스로 등록해준다 / 컴포넌트 어노테이션을 내포하고 있다
@Transactional // 쿼리를 실행하는 DAO를 가져다 쓰는 곳에는 해당 어노테이션을 작성해야한다 -> class 전체에 붙여도 되지만 동작하는 해당 메서드에만 붙여도 된다 ex) join메서드
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Long join(member member){
        ChkOver(member);
        memberRepository.save(member);
        return member.getId();
    }
    
    // 중복확인
    private void ChkOver(member member) {
        memberRepository.findByName(member.getName()) // ifPresent는 값이 있을경우 원하는 동작을 하도록 지정한다
                .ifPresent(m -> {
                    throw new IllegalStateException("Already Exist Account !");
                });
    }

    public List<member> findMember(){
        return memberRepository.findAll();
    }

    public Optional<member> findOne(Long memId){
        return memberRepository.findById(memId);
    }
}
