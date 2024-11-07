package org.demointernetshop47fs.controller;

import lombok.RequiredArgsConstructor;
import org.demointernetshop47fs.controller.api.UserApi;
import org.demointernetshop47fs.dto.StandardResponseDto;
import org.demointernetshop47fs.dto.UserDto;
import org.demointernetshop47fs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService service;


    @Override
    public ResponseEntity<UserDto> getUserById(long userId) {
        return ResponseEntity.ok(service.getUserById(userId));
    }

    //
    @GetMapping("/photolink")
    public ResponseEntity<StandardResponseDto> setPhotoLink(@RequestParam String fileLink){
        return ResponseEntity.ok(service.setPhotoLink(fileLink));
    }
}
