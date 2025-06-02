package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Member;
import com.lec.spring.service.BoardService;
import com.lec.spring.service.MemberService;
import com.lec.spring.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.IModel;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Autowired
    NoticeService noticeService;

    @GetMapping("/home")
    public String home(Model model,Authentication authentication){

        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();


        model.addAttribute("containerRightPage", "mypage/view");

        model.addAttribute("member",memberService.findById(userDetails.getMember().getId()));

        return "mypage/home";
    }

    @GetMapping("/view")
    public  String  view(Model model  ,Authentication authentication){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();

        model.addAttribute("member",memberService.findById(userDetails.getMember().getId()));

        return  "mypage/view";
    }

    @GetMapping("/modify")
    public  void  modify(Model model  ,Authentication authentication){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();

        model.addAttribute("member",memberService.findById(userDetails.getMember().getId()));

    }

    @PostMapping("/modify")
    public  String modifyoK(Member member,Model model){




        model.addAttribute("result",memberService.mypageupdate(member));



        return "mypage/updateOk";
    }


    // 게시글
    @GetMapping("/myPosts")
    public  void  myPosts(Model model,Authentication authentication){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();

        model.addAttribute("post",boardService.myposts(userDetails.getMember().getId()));

    }


    // 삭제
    @PostMapping("/delete")
    public ResponseEntity<Integer> memberdelete(@RequestParam String username){
        int result = memberService.mypagedelete(username);

        return ResponseEntity.ok(result);
    }





}
