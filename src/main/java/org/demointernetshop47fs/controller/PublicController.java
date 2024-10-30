package org.demointernetshop47fs.controller;

import lombok.RequiredArgsConstructor;
import org.demointernetshop47fs.controller.api.PublicApi;
import org.demointernetshop47fs.dto.NewUserDto;
import org.demointernetshop47fs.dto.UserDto;
import org.demointernetshop47fs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublicController implements PublicApi {

    private final UserService service;


    @Override
    public ResponseEntity<UserDto> userRegistration(NewUserDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.registration(request));
    }

    @Override
    public ResponseEntity<UserDto> confirmRegistration(String code) {
        return ResponseEntity.ok(service.confirmation(code));
    }
}
