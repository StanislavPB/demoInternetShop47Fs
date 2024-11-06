package org.demointernetshop47fs.controller;

import org.demointernetshop47fs.entity.ConfirmationCode;
import org.demointernetshop47fs.entity.User;
import org.demointernetshop47fs.repository.ConfirmationCodeRepository;
import org.demointernetshop47fs.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class PublicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationCodeRepository confirmationCodeRepository;



    @BeforeEach
    void setUp(){
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
    void drop(){
        confirmationCodeRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void testRegisterUser() throws Exception {

        String newUserJson = """
                {
                "firstName":"John",
                "lastName":"User",
                "email":"john@gmail.com",
                "hashPassword":"Pass12345!"
                }
                """;

        mockMvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("john@gmail.com"));
    }

    @Test
    void testReturn400ForBadFormatEmail() throws Exception {

        String newUserJson = """
                {
                "firstName":"John",
                "lastName":"User",
                "email":"badFormatEmail",
                "hashPassword":"Pass12345!"
                }
                """;

        mockMvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testReturn409ForExistEmail() throws Exception {

        String newUserJson = """
                {
                "firstName":"John",
                "lastName":"User",
                "email":"user1@gmail.com",
                "hashPassword":"Pass12345!"
                }
                """;

        mockMvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isConflict());
    }

    @Test
    void testRegisterForExistEmail() throws Exception {

        String newUserJson = """
                {
                "firstName":"John",
                "lastName":"User",
                "email":"user1@gmail.com",
                "hashPassword":"Pass12345!"
                }
                """;

        mockMvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Пользователь с email: user1@gmail.com уже зарегистрирован"));
    }


    @Test
    void testConfirmRegistration() throws Exception {
        mockMvc.perform(get("/api/public/confirm")
                .param("code","someConfirmationCode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user1@gmail.com"));
    }



}