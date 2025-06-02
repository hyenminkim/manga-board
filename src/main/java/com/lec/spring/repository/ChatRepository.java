package com.lec.spring.repository;

import com.lec.spring.domain.Chatroom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chatroom, Long> {


}
