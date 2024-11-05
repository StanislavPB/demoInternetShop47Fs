package org.demointernetshop47fs.controller;

import lombok.RequiredArgsConstructor;
import org.demointernetshop47fs.controller.api.AuthApi;
import org.demointernetshop47fs.security.dto.AuthRequest;
import org.demointernetshop47fs.security.dto.AuthResponse;
import org.demointernetshop47fs.security.service.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    @Override
    public ResponseEntity<AuthResponse> authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(request.getUsername());

        return new ResponseEntity<>(new AuthResponse(jwt), HttpStatus.OK);
    }
}
