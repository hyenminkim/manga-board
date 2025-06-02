package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Chatroom;
import com.lec.spring.domain.Member;
import com.lec.spring.service.ChatService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Chatroom> chatroomList = chatService.list();
        model.addAttribute("chatroom", chatroomList);
        return "chat/list";
    }

    @GetMapping("/room/{room_id}")
    public String room(@PathVariable Long room_id, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();

        Chatroom  chatroom = chatService.findById(room_id);

        if(chatroom == null) {
            model.addAttribute("err",1);
            return "chat/room";
        }

        model.addAttribute("room_id", room_id);
        model.addAttribute("username", userDetails.getMember().getName());
        return "chat/room";
    }

    @GetMapping("/chatcreate")
    @ResponseBody
    public ResponseEntity<String> create(@RequestParam Long member_id, @RequestParam String title) {


        chatService.chatcreate(member_id,title);
        return ResponseEntity.ok("채팅방 생성 완료");
    }

    @PostMapping("/delete")
    public ResponseEntity<Integer>delete(@RequestParam Long id){
        int result = chatService.delete(id);

        return ResponseEntity.ok(result);
    }

}

