package com.lec.spring.service;

import com.lec.spring.domain.Chatroom;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ChatService {

    public void chatcreate(Long member_id, String title);

    public List<Chatroom> list();

    public Chatroom findById(Long room_id);

    public int delete(Long id);
}
