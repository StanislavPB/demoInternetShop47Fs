package org.demointernetshop47fs.controller.api;

import org.demointernetshop47fs.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
public interface AdminApi {

    @GetMapping("/users")
    ResponseEntity<List<UserDto>> findAll();

//    @DeleteMapping("/users/{userId}")
//    ResponseEntity<List<UserDto>> deleteUser(@PathVariable Long userId);

    @GetMapping("/bann")
    ResponseEntity<UserDto> makeUserBan(@RequestParam String email);

}
