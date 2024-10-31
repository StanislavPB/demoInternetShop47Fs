package org.demointernetshop47fs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demointernetshop47fs.dto.NewUserDto;
import org.demointernetshop47fs.dto.UserDto;
import org.demointernetshop47fs.entity.ConfirmationCode;
import org.demointernetshop47fs.entity.User;
import org.demointernetshop47fs.repository.ConfirmationCodeRepository;
import org.demointernetshop47fs.repository.UserRepository;
import org.demointernetshop47fs.service.exception.AlreadyExistException;
import org.demointernetshop47fs.service.exception.NotFoundException;
import org.demointernetshop47fs.service.mail.MailUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final MailUtil mailUtil;


    @Transactional
    public UserDto registration(NewUserDto newUser){

        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new AlreadyExistException("Пользователь с email: " + newUser.getEmail() + " уже зарегистрирован");
        }

        User user = User.builder()
                .email(newUser.getEmail())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .hashPassword(newUser.getHashPassword())
                .role(User.Role.USER)
                .state(User.State.NOT_CONFIRMED)
                .build();

        userRepository.save(user);

        String code = UUID.randomUUID().toString();

        saveConfirmCode(user,code);

        // отправка кода по электронной почте

        sendEmail(user,code);

        return UserDto.from(user);

    }

    private void sendEmail(User user, String code) {
        String link = "http://localhost:8080/api/public/confirm?code=" + code;
        log.info("ссылка для отправки email: {}", link);
        mailUtil.send(
                user.getFirstName(),
                user.getLastName(),
                link,
                "Code confirmation email",
                user.getEmail());
    }

    private void saveConfirmCode(User newUser, String codeUUID){
        ConfirmationCode confirmationCode = ConfirmationCode.builder()
                .code(codeUUID)
                .user(newUser)
                .expiredDateTime(LocalDateTime.now().plusDays(1))
                .build();

        confirmationCodeRepository.save(confirmationCode);
    }


    @Transactional
    public UserDto confirmation(String confirmCode){

        ConfirmationCode code = confirmationCodeRepository
                .findByCodeAndExpiredDateTimeAfter(confirmCode, LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Код подтверждения не найден или его срок действия истек"));

        User user = code.getUser();
        user.setState(User.State.CONFIRMED);
        userRepository.save(user);

        return UserDto.from(user);

    }


    public List<UserDto> findAll(){
        return UserDto.from(userRepository.findAll());
    }

    public UserDto getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден"));
        return UserDto.from(user);
    }


    @Transactional
    public UserDto makeUserBanned(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь с email " + email + " не найден"));

        user.setState(User.State.BANNED);
        userRepository.save(user);

        return UserDto.from(user);
    }


}
