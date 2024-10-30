package org.demointernetshop47fs.controller;

import lombok.RequiredArgsConstructor;
import org.demointernetshop47fs.controller.api.UserApi;
import org.demointernetshop47fs.dto.UserDto;
import org.demointernetshop47fs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService service;


    @Override
    public ResponseEntity<UserDto> getUserById(long userId) {
        return ResponseEntity.ok(service.getUserById(userId));
    }
}
