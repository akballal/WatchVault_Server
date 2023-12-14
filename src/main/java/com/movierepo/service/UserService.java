package com.movierepo.service;

import com.movierepo.config.JwtService;
import com.movierepo.entity.User;
import com.movierepo.exception.IncorrectUsernameOrPasswordException;
import com.movierepo.repository.UserRepo;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<String> signUp(User user) {
        Optional<User> IfUserExist = userRepo.findByUsername(user.getUsername());
        if (IfUserExist.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exist!! Please try with different Username");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(jwtToken);
    }

    public ResponseEntity<String> login(User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword()
                    )
            );
        }
        catch (AuthenticationException e)
        {
            throw new IncorrectUsernameOrPasswordException("Incorrect username or password");
        }
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(jwtToken);
    }

    public ResponseEntity<String> authenticate(String token) {
        try{
        if (!jwtService.isTokenExpired(token)) {
            return ResponseEntity.ok(jwtService.extractUsername(token));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Session expired, please login again.");
        }}
        catch (JwtException e)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Session expired, please login again.");
        }
    }
}
