package com.sudhir.springai.chatbot.controller;

import com.sudhir.springai.chatbot.dto.ChatRequest;
import com.sudhir.springai.chatbot.dto.ChatResponse;
import com.sudhir.springai.chatbot.service.ChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public String chat(@RequestBody ChatRequest request) {
        return chatService.chat(request.getMessage());
    }

    @PostMapping("/chatWithStructure")
    public ChatResponse chatWithStructure(@RequestBody ChatRequest request, HttpSession session) {
        String conversationId = session.getId();
        return chatService.chatWithStructure(request.getMessage(), conversationId);
    }
}