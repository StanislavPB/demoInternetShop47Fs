package org.demointernetshop47fs.repository;

import org.demointernetshop47fs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

   Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
