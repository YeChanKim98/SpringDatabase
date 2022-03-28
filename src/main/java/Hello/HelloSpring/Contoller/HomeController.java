package Hello.HelloSpring.Contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // 일반 로컬호스트 8080으로 접속시 호출
    public String home(){
        return "home";
    }



}
