package org.example.book_library.service.imple;

import lombok.RequiredArgsConstructor;
import org.example.book_library.domain.Role;
import org.example.book_library.domain.User;
import org.example.book_library.dto.request.UserRegistrationRequest;
import org.example.book_library.dto.response.UserRegistrationResponse;
import org.example.book_library.exception.custom.UserAlreadyExistsException;
import org.example.book_library.repository.inter.UserRepositoryInter;
import org.example.book_library.security.JWTService;
import org.example.book_library.service.inter.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImple implements AuthService {
    private final UserRepositoryInter userRepositoryInter;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest) {
        if(userRepositoryInter.findByUsername(userRegistrationRequest.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("Username " + userRegistrationRequest.getUsername()+ " already exists");
        }

        User user = User.builder()
                .username(userRegistrationRequest.getUsername())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        user = userRepositoryInter.save(user);

        return UserRegistrationResponse.builder()
                .username(user.getUsername())
                .success(true)
                .message("User registered successfully!")
                .build();
    }

    @Override
    public String login(String username, String password) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userRepositoryInter.findByUsername(username).orElseThrow();

        Map<String,Object> map = new HashMap<>();
        map.put("role",user.getRole().name());
        map.put("id", user.getId());

        return jwtService.generateJWTToken(map,user);


    }
}
