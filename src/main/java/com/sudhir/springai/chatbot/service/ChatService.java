package com.sudhir.springai.chatbot.service;

import com.sudhir.springai.chatbot.dto.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    public String chat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    public ChatResponse chatWithStructure(String message, String conversationId) {
        String response = chatClient.prompt()
                .system("You are a helpful AI assistant")
                .user(message)
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
                .call()
                .content();

        return new ChatResponse(response);
    }

    public Flux<String> streamChat(String message, String conversationId) {
        Flux<String> response = chatClient.prompt()
                .system("You are a helpful AI assistant")
                .user(message)
                .advisors(a -> a.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
                .stream()
                .content();

        return response;
    }
}
