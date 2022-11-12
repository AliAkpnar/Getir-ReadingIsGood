package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.advice.AppControllerAdvice;
import com.getir.readingisgood.model.dto.LoginDto;
import com.getir.readingisgood.model.dto.SignUpDto;
import com.getir.readingisgood.model.response.JWTAuthResponse;
import com.getir.readingisgood.persistence.entity.User;
import com.getir.readingisgood.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIT {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("1234"));
        mockMvc = MockMvcBuilders.standaloneSetup(authController).setControllerAdvice(AppControllerAdvice.class).build();
        if (!userRepository.existsByEmail("test@test.com"))
            userRepository.save(user);
    }

    @Test
    void should_authenticate_user_when_login() throws Exception {
        // Given
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("1234");
        loginDto.setUsernameOrEmail("test@test.com");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loginDto));
        // When
        ResultActions perform = mockMvc.perform(requestBuilder);
        // Then
        JWTAuthResponse jwtAuthResponse = objectMapper.readValue(perform.andReturn().getResponse()
                .getContentAsString(), JWTAuthResponse.class);

        assertNotNull(jwtAuthResponse.getAccessToken());
        assertNotNull(jwtAuthResponse.getTokenType());
    }

    @Test
    void should_authenticate_user_when_register() throws Exception {
        // Given
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("tesst@test.com");
        signUpDto.setName("tesst");
        signUpDto.setUsername("tesst");
        signUpDto.setPassword("12345");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signUpDto));
        // When
        ResultActions perform = mockMvc.perform(requestBuilder);
        // Then
        String response = perform.andReturn().getResponse().getContentAsString();

        assertEquals(response, "User registered successfully");
    }
}
