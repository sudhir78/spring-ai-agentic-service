package com.sudhir.springai.chatbot.controller;


import com.sudhir.springai.chatbot.dto.ChatRequest;
import com.sudhir.springai.chatbot.service.ChatService;
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
}
