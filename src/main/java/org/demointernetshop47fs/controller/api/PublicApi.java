package org.demointernetshop47fs.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.demointernetshop47fs.dto.ErrorResponseDto;
import org.demointernetshop47fs.dto.NewUserDto;
import org.demointernetshop47fs.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/public")
public interface PublicApi {

    @Operation(summary = "Регистрация пользователя", description = "операция доступна всем, роль по умолчанию - USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    }
    )
    @PostMapping("/register")
    public ResponseEntity<UserDto> userRegistration(@Valid @RequestBody NewUserDto request);

    @GetMapping("/confirm")
    public ResponseEntity<UserDto> confirmRegistration(@RequestParam String code);

}
