package com.chat.service;

import com.chat.domain.entities.User;
import com.chat.domain.models.binding.UserRegisterBindingModel;
import com.chat.domain.models.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    boolean register(UserRegisterBindingModel bindingModel, BindingResult bindingResult);

    List<UserViewModel> findAll();

    User findById(Integer id);
}
