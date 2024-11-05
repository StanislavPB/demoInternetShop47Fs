package org.demointernetshop47fs.controller.api;

import jakarta.validation.Valid;
import org.demointernetshop47fs.security.dto.AuthRequest;
import org.demointernetshop47fs.security.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface AuthApi {

    @PostMapping
    ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request);
}
