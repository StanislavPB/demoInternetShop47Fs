package org.demointernetshop47fs.controller.api;

import org.demointernetshop47fs.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/users")
public interface UserApi {

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId);

    // можно добавить updateUser
    /*
    @PutMapping("/{userId}")

     */
}
