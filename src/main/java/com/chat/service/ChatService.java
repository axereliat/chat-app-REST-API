package com.chat.service;

import com.chat.domain.models.binding.ChatCreateBindingModel;

import java.security.Principal;

public interface ChatService {

    boolean createChat(Principal principal, ChatCreateBindingModel bindingModel);
}
