package com.chat.web.controllers;

import com.chat.domain.models.binding.UserRegisterBindingModel;
import com.chat.domain.models.view.ErrorViewModel;
import com.chat.domain.models.view.SuccessViewModel;
import com.chat.domain.models.view.UserViewModel;
import com.chat.service.UserService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity register(@RequestBody @Valid UserRegisterBindingModel bindingModel, BindingResult bindingResult) {
        boolean result = this.userService.register(bindingModel, bindingResult);

        if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            return new ResponseEntity(new ErrorViewModel(List.of("Passwords do not match.")), HttpStatus.BAD_REQUEST);
        }

        if (!result) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return new ResponseEntity(new ErrorViewModel(errors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new SuccessViewModel("User successfully registered."), HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserViewModel> getAll() {
        return this.userService.findAll();
    }
}
