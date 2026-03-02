package com.example.demo.Controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Component.JwtUtil;
import com.example.demo.Model.User;
import com.example.demo.Model.User.Role;
import com.example.demo.Repository.UserRepository;


@RestController
@RequestMapping("/api/auth")

public class AuthController {
    


    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
   
    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User req) {

        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email exists");
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        Set<Role> roles = (req.getRoles() == null || req.getRoles().isEmpty())
        ? new HashSet<>(Set.of(Role.USER))  // default role
        : new HashSet<>(req.getRoles());     // roles from request

        user.setRoles(roles);


        try {
            userRepository.save(user);
            return ResponseEntity.status(201).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User req) {
        //TODO: process POST request

        if (req.getEmail() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        } 
        
        Optional<User> dbuser = userRepository.findByEmail(req.getEmail());
        if (!dbuser.isPresent()) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        User dbuser2 = dbuser.get();
        if (!passwordEncoder.matches(req.getPassword(), dbuser2.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(dbuser2);
        UUID uid = dbuser2.getUuid();

        System.out.println(dbuser2);

        // Return token in a JSON object
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userid", String.valueOf(uid));

        return ResponseEntity.ok(response);

    }
    



}
