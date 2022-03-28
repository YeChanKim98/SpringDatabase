package Hello.HelloSpring.Contoller;

import Hello.HelloSpring.domain.member;
import Hello.HelloSpring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // 해당 어노테이션을 달고 있으면, 스프링 컨테이너가 컨트롤러 객체를 생성 후 알아서 보관한다
public class MemberController {

    // New를 통해서 객체를 생성하게 되면, 다른 파일에서도 해당 방법으로 객체를 가져다가 쓰기때문에
    // 하나의 변수만 만들어서 스프링 컨터이너에 등록한다 : 컨테이너에 등록은 중복될 수 없다 -> 결과, 하나의 개체를 모두가 공유해서 사용한다
    private final MemberService memberService;

    @Autowired // 인자를 스프링 컨테이너에 있는 개체중 이름이 동일한 개체로 매칭을 시켜준다
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("members/new") // Post로 값을 받을 때 매핑
    public String create(MemberForm form){
        member member = new member();
        member.setName(form.getName());
        memberService.join(member);

        return "redirect:/";
        // 작성까지만 하고 회원 웹 기능 - 등록 중간 까지 강의 봄
    }
    
    @GetMapping("/members")
    public String list(Model model){
        List<member> members = memberService.findMember();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
