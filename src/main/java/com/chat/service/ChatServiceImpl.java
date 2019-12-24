package com.chat.service;

import com.chat.domain.entities.Chat;
import com.chat.domain.entities.User;
import com.chat.domain.models.binding.ChatCreateBindingModel;
import com.chat.repository.ChatRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Set;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final UserService userService;

    private final ChatRepository chatRepository;

    public ChatServiceImpl(UserService userService, ChatRepository chatRepository) {
        this.userService = userService;
        this.chatRepository = chatRepository;
    }

    @Override
    public boolean createChat(Principal principal, ChatCreateBindingModel bindingModel) {
        User currentUser = this.userService.findByUsername(principal.getName());

        User otherUser;
        try {
            otherUser = this.userService.findById(bindingModel.getUserId());
        } catch (NullPointerException ex) {
            return false;
        }

        Chat chat = new Chat();
        chat.setUsers(Set.of(currentUser, otherUser));
        this.chatRepository.saveAndFlush(chat);

        return true;
    }
}
