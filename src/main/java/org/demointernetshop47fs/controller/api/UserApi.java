package org.demointernetshop47fs.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.demointernetshop47fs.dto.ErrorResponseDto;
import org.demointernetshop47fs.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/users")
public interface UserApi {

    @Operation(summary = "Получение информации о пользователе по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId);

    // можно добавить updateUser
    /*
    @PutMapping("/{userId}")

     */
}
