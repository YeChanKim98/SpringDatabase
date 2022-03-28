package Hello.HelloSpring.Contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    
    // 작동 설명
    // 1. Get메서드를 통해서 hello라는 값이 전달된다
    // 2. 톰캣에서 Model을 만들어서 매핑된 hello 메서드에 넘겨준다
    // 3. 메서드에서 모델에 새로운 데이터를 추가한다
    // 4-1. 리턴으로 hello를 주었는데, 이는 templates에 있는 같은 이름의 파일에 넘겨주는 것을 의미한다 : 컨트롤러에서 리턴값으로 문자열을 반환 시, 템플릿폴더/문자열.html이라는 파일을 만들어서 반환한다
    // 4-2. 즉 모델에 data라는 변수에 Hello를 넣은 후 hello에 넘겨준다
    // 5. 넘겨 받은 파일은 data라는 변수의 값을 치환하여, 출력한다
    @GetMapping("hello")
    public String hello(Model model){ //MVC의 Model
        model.addAttribute("data", "Hello!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    //name이라는 파라미터의 값을 받아와 String name에 넣는다
    //요청을 False로 두어 필수가 아님
    public String hellomvc(@RequestParam(value="name", required = false) String name, Model model){
        model.addAttribute("name",name);
        return "hello-mvc";
    }

    // 위 두가지 예제는 템플릿을 이용한 것이고, 아래 예제는 템플릿없이 http바디에 원하는 내용을 바로 return해주는 예제이다
    // 따라서 model을 받아올 필요도 없고 그에따라 return으로 바로 값을 넘길 수 있다
    // return으로 넘긴 값은 html태그를 붙이지 않는 이상. 아무 태그 및 기타 요소 없이, 입력한 내용만 넘어간다
    @GetMapping("no-temp")
    @ResponseBody // HttpBody에 반환할 값을 HttpMessageConverter를 통해서 변환 후 반환하며, 일반 문자는 String Converter, 객체 및 기타 자료형은 Json Converter 를 이용하여 적합한 형태로 변환한다
    public String notemp(@RequestParam(value="name", required = false) String name){
        return "Hello Non-Templet Engine / Nmae : "+name;
    }

    // 객체를 만든 후 객체를 통해서 반환을 할 수도 있다
    // 객체의 반환시 html태그 및 기타 방법으로 가공하지 않을 시, json문법(키/벨류 구조)으로 그냥 넘어간다(대괄호, 컴마 등이 중간에 찍혀있을 것임)
    // @ResponseBody가 기본적으로 json의 방식으로 반환함
    static class hello_api{ // Static을 이용하여 calss내부에 class를 제작
        private String name;
        
        // Property 접근 방식
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    
    @GetMapping("Hello-api")
    @ResponseBody
    public hello_api hello_api(@RequestParam("name") String name){ // 위에서 만든 hello_api타입의 메소드를 만들어준다
        hello_api hello = new hello_api();
        hello.setName(name);
        return hello;
    }

}
