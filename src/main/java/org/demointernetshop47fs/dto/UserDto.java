package org.demointernetshop47fs.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.demointernetshop47fs.entity.ConfirmationCode;
import org.demointernetshop47fs.entity.User;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;

    private String email;

    private String role;

    public static UserDto from(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }

    public static List<UserDto> from(List<User> users){
        return users.stream()
                .map(user -> UserDto.from(user))
                .toList();
    }

}
