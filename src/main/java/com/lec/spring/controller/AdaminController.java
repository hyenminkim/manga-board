package com.lec.spring.controller;


import com.lec.spring.domain.Member;
import com.lec.spring.service.ChatService;
import com.lec.spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdaminController {


    @Autowired
    MemberService memberService;


    @Autowired
    ChatService chatService;


    @RequestMapping("/home")
    public void home(){

    }


    @RequestMapping("/notice")
    public void notice(){

    }


    @RequestMapping("/user")
    public void user(Model model){

        List<Member> members = memberService.memberlist();

        model.addAttribute("list",members);



    }

    @GetMapping("/user_modify/{id}")
    public String user_modify(@PathVariable Long id , Model model) {

        Optional<Member> member = memberService.findByuser(id);


        model.addAttribute("member",member.get());

        return "admin/user_modify" ;
    }


    @PostMapping("/user_modify")
    public String user_modifycheck(Member member,@RequestParam String role,Model model){


        model.addAttribute("result",memberService.update(member,role));

        return "admin/updateOk" ;
    }

    @PostMapping("/delete")
    public ResponseEntity<Integer>memberdelete(@RequestParam Long id){
        int result = memberService.memberdelete(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/post")
    public  void  post(Model model){

        model.addAttribute("post",memberService.post());


    }

    @GetMapping("/chat")
    public void chat(Model model){

        model.addAttribute("list",chatService.list());
    }


}
