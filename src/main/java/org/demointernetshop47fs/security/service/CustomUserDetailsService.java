
package org.demointernetshop47fs.security.service;

import lombok.RequiredArgsConstructor;
import org.demointernetshop47fs.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .map(UserToUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }
}
