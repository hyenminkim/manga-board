package com.lec.spring.service;


import com.lec.spring.domain.Chatroom;
import com.lec.spring.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public void chatcreate(Long member_id, String title) {
        Chatroom chatroom = new Chatroom();

        chatroom.setMember_id(member_id);
        chatroom.setTitle(title);


        chatRepository.save(chatroom);
    }

    @Override
    public List<Chatroom> list() {
        return chatRepository.findAll();
    }

    @Override
    public Chatroom findById(Long room_id) {
        return chatRepository.findById(room_id).orElse(null);
    }

    @Override
    public int delete(Long id) {

        if(chatRepository.existsById(id)){

            chatRepository.deleteById(id);

            return 1;
        }

        return 0;


    }
}
