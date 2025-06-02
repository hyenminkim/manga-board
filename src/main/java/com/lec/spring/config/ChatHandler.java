package com.lec.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec.spring.domain.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final Map<Long, Set<WebSocketSession>> chatRoomSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("WebSocket 연결됨: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessageDto dto = mapper.readValue(message.getPayload(), ChatMessageDto.class);

        chatRoomSessions.computeIfAbsent(dto.getChatRoomId(), k -> new HashSet<>()).add(session);

        // LEAVE인 경우 세션 제거
        if (dto.getMessageType() == ChatMessageDto.MessageType.LEAVE) {
            chatRoomSessions.get(dto.getChatRoomId()).remove(session);
        }

        // 전체 세션에 메시지 전달
        for (WebSocketSession s : chatRoomSessions.get(dto.getChatRoomId())) {
            s.sendMessage(new TextMessage(mapper.writeValueAsString(dto)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("WebSocket 연결 종료: {}", session.getId());
        chatRoomSessions.values().forEach(sessions -> sessions.remove(session));
    }
}
