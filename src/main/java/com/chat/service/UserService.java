package com.chat.service;

import com.chat.domain.entities.User;
import com.chat.domain.models.binding.UserRegisterBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    boolean register(UserRegisterBindingModel bindingModel, BindingResult bindingResult);
}
