package com.lec.spring.controller;



import com.lec.spring.domain.Member;
import com.lec.spring.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    MemberService memberService;




    @RequestMapping("/login")
    public void login(){

    }

    @PostMapping("/loginError")
    public String loginError(){
        return "user/login";
    }

    @RequestMapping("/logincheck")
    public void  logincheck(){

    }


    @RequestMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(Member member){

        memberService.save(member);

        return "redirect:/user/login";
    }


    @RequestMapping("/rejectAuth")
    public String rejectAuth(){
        return "common/rejectAuth";
    }


   @PostMapping("/ConfirmId")
    public ResponseEntity confirmId(@RequestParam String username){


        boolean result = memberService.selectusername(username);


       return  ResponseEntity.ok(result);
   }

}
