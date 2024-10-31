package org.demointernetshop47fs.controller;

import lombok.RequiredArgsConstructor;
import org.demointernetshop47fs.controller.api.AdminApi;
import org.demointernetshop47fs.dto.UserDto;
import org.demointernetshop47fs.entity.ConfirmationCode;
import org.demointernetshop47fs.entity.User;
import org.demointernetshop47fs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final UserService service;

    @Override
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<UserDto> makeUserBan(String email) {
        return ResponseEntity.ok(service.makeUserBanned(email));
    }

    @Override
    public ResponseEntity<List<User>> findAllFull() {
        return ResponseEntity.ok(service.findAllFull());
    }

    @Override
    public ResponseEntity<List<ConfirmationCode>> findAllCodes(String email) {
        return ResponseEntity.ok(service.findCodesByUser(email));
    }


}
