package Hello.HelloSpring;
import Hello.HelloSpring.repository.*;
import Hello.HelloSpring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

// 메모리용
//    @Bean
//    public MemberService memberService(){
//        return new MemberService(memberRepository());
//    }

//========================================================================

    // JDBC용
    /*
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
    */
//============================================================================

    // JPA용
    // @PersistenceContext // 오토와이어드와 생성자 필요없이 해당 어노테이션으로 대체 가능
//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }


    // Spring Data JPA용
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) { // 현재 빈이 두개 등록되어서 퀄리파이로 지정해줌
        this.memberRepository = memberRepository;
    }

    //@Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository(){
//        // MemberRepository는 Interface(구현체)이기 때문에 MemoryMemberRepository로 반환을 해준다
//        // return new MemoryMemberRepository(); // Memory
//        // return new JdbcMemberRepository(dataSource); // JDBC
//        // return new JdbcTemplateMemberRepository(dataSource); // JDBC Template
//        //return new JpaMemberRepository(em);
//        return null; // Spring Data JPA는 해당 메소드를 필요치 않는다
//    }


}
