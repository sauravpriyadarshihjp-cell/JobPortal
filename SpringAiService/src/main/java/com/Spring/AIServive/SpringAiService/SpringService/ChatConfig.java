package com.Spring.AIServive.SpringAiService.SpringService;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
    @Bean
    public ChatClient chatClient(){
        OpenAiApi api = new OpenAiApi("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");
        OpenAiChatModel chatModel = new OpenAiChatModel(api);
        return ChatClient.create(chatModel);
    }
}
