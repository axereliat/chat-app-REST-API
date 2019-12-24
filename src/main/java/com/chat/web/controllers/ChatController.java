package com.chat.web.controllers;

import com.chat.domain.models.binding.ChatCreateBindingModel;
import com.chat.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController("/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity createChat(Principal principal, @RequestBody ChatCreateBindingModel bindingModel) {
        boolean result = this.chatService.createChat(principal, bindingModel);
        if (!result) {
            return new ResponseEntity(Map.of("error", "User with id " + bindingModel.getUserId() + "does not exist."),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Map.of("message", "Chat successfully created"), HttpStatus.CREATED);
    }
}
