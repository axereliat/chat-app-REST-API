package com.chat.service;

import com.chat.common.constants.Constants;
import com.chat.domain.entities.Role;
import com.chat.domain.entities.User;
import com.chat.domain.models.binding.UserRegisterBindingModel;
import com.chat.domain.models.view.UserViewModel;
import com.chat.repository.RoleRepository;
import com.chat.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    private void seedRoles() {
        if (this.roleRepository.findAll().size() == 0) {
            Role userRole = new Role();
            userRole.setAuthority(Constants.USER_ROLE);

            this.roleRepository.saveAndFlush(userRole);
        }
    }

    @Override
    public boolean register(UserRegisterBindingModel bindingModel, BindingResult bindingResult) {
        if (bindingResult.getAllErrors().size() > 0) {
            return false;
        }

        this.seedRoles();

        User user = this.modelMapper.map(bindingModel, User.class);
        user.setPassword(this.passwordEncoder.encode(bindingModel.getPassword()));

        user.addAuthority(this.roleRepository.findByAuthority(Constants.USER_ROLE));

        this.userRepository.saveAndFlush(user);

        return true;
    }

    @Override
    public List<UserViewModel> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User findById(Integer id) {
        return this.userRepository.findById(id).orElseThrow(() -> {
            throw new NullPointerException("User with id " + id + " not found");
        });
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!this.userRepository.existsByUsername(s)) {
            throw new UsernameNotFoundException("Username " + s + " not found.");
        }
        return this.userRepository.findByUsername(s);
    }
}
