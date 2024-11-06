package org.demointernetshop47fs.controller;

import org.demointernetshop47fs.dto.NewUserDto;
import org.demointernetshop47fs.entity.ConfirmationCode;
import org.demointernetshop47fs.entity.User;
import org.demointernetshop47fs.repository.ConfirmationCodeRepository;
import org.demointernetshop47fs.repository.UserRepository;
import org.demointernetshop47fs.service.UserService;
import org.demointernetshop47fs.service.exception.AlreadyExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationCodeRepository confirmationCodeRepository;

    //@MockBean
    @Autowired
    private UserService userService;


    @BeforeEach
    void setUp() {
        User testUser = new User();
        testUser.setFirstName("user1");
        testUser.setLastName("user1");
        testUser.setEmail("user1@gmail.com");
        testUser.setHashPassword("Pass12345!");
        testUser.setRole(User.Role.USER);
        testUser.setState(User.State.NOT_CONFIRMED);
        User savedUser = userRepository.save(testUser);

        ConfirmationCode code = new ConfirmationCode();
        code.setCode("someConfirmationCode");
        code.setUser(savedUser);
        code.setExpiredDateTime(LocalDateTime.now().plusDays(1));

        confirmationCodeRepository.save(code);
    }

    @AfterEach
    void drop() {
        confirmationCodeRepository.deleteAll();
        userRepository.deleteAll();
   }


    @Test
    public void testWhenNoAuthenticationThenReturn403() throws Exception {
        mockMvc.perform(get("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testWhenNoAuthorizeRoleThenReturn403() throws Exception {
        mockMvc.perform(get("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testWhenReturn200ForAdminRequest() throws Exception {
        mockMvc.perform(get("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    // тест сервиса

    @Test
    void testWhenDuplicateEmail() {

        NewUserDto newUserDto = new NewUserDto("firstUserName", "lastUserName", "user1@gmail.com", "Pass12345!");

        //UserDto userDto = userService.registration(newUserDto);

        //doThrow(new AlreadyExistException("Email is already exist")).when(userService).registration(any());

        assertThrows(AlreadyExistException.class, () ->
                userService.registration(newUserDto));

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testWhenReturn200ForUserRequest() throws Exception {
        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user1@gmail.com"));
    }


}