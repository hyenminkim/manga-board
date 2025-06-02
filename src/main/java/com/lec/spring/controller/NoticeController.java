package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Notice;
import com.lec.spring.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {


    @Autowired
    NoticeService noticeService;


    @PostMapping("/write")
    public  void noticeadd(@RequestParam String subject, @RequestParam String contents, Authentication authentication){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();

        Notice notice = new Notice();

        notice.setSubject(subject);
        notice.setWrite_name(userDetails.getMember().getName());
        notice.setContents(contents);

        noticeService.noticecreate(notice);


    }

    @GetMapping("/list")
    public List<Notice> noticelist(){

        return noticeService.noticelist();
    }

    @PostMapping("/delete")
    public  int noticedelete(@RequestParam Long id){
        return noticeService.noticedelete(id);
    }
}
