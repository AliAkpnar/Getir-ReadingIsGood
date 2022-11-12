package com.getir.readingisgood.controller;

import com.getir.readingisgood.advice.exception.EmailAlreadyTakenException;
import com.getir.readingisgood.advice.exception.UsernameAlreadyTakenException;
import com.getir.readingisgood.model.dto.LoginDto;
import com.getir.readingisgood.model.dto.SignUpDto;
import com.getir.readingisgood.model.response.JWTAuthResponse;
import com.getir.readingisgood.persistence.entity.User;
import com.getir.readingisgood.persistence.repository.UserRepository;
import com.getir.readingisgood.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController extends BaseController {

    public static final String TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("auth/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        log.info("Authenticate User : {}", loginDto.getUsernameOrEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        log.info("Token : {}", token);
        return ResponseEntity.ok(new JWTAuthResponse(token, TOKEN_TYPE));
    }

    @PostMapping("auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        log.info("Register User : {}", signUpDto.getUsername());
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new UsernameAlreadyTakenException();
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new EmailAlreadyTakenException();
        }
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        /*Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));*/
        userRepository.save(user);
        log.info("Saved User : {}", signUpDto.getUsername());
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}
