package org.demointernetshop47fs.controller.api;

import jakarta.validation.Valid;
import org.demointernetshop47fs.dto.NewUserDto;
import org.demointernetshop47fs.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/public")
public interface PublicApi {

    @PostMapping("/register")
    public ResponseEntity<UserDto> userRegistration(@Valid @RequestBody NewUserDto request);

    @PostMapping("/confirm")
    public ResponseEntity<UserDto> confirmRegistration(@RequestParam String code);

}
