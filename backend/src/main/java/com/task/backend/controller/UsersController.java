package com.task.backend.controller;

import com.task.backend.components.JwtTokenUtil;
import com.task.backend.dto.LoginDto;
import com.task.backend.dto.LoginResponseDto;
import com.task.backend.dto.SignUpDto;
import com.task.backend.model.Role;
import com.task.backend.model.User;
import com.task.backend.repository.RoleRepository;
import com.task.backend.repository.UserRepository;
import com.task.backend.service.userService.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class UsersController {

    @RestController
    @RequestMapping("/api")
    public class HomeController {
        @Autowired
        private AuthenticationManager authenticationManager;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private RoleRepository roleRepository;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private JwtTokenUtil jwtTokenUtil;

        @Autowired
        private JwtUserDetailsService userDetailsService;

        @RequestMapping(value = "/login", method = RequestMethod.POST)
        public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(loginDto.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponseDto(token).toString());
        }


        @PostMapping("/signup")
        public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
            // checking for username exists in a database
            if(userRepository.existsByName(signUpDto.getUsername())){
                return new ResponseEntity<>("Username is already exist!", HttpStatus.BAD_REQUEST);
            }
            // checking for email exists in a database
            if(userRepository.existsByEmail(signUpDto.getEmail())){
                return new ResponseEntity<>("Email is already exist!", HttpStatus.BAD_REQUEST);
            }
            // creating user object
            User user = new User();
            user.setName(signUpDto.getName());
            user.setEmail(signUpDto.getEmail());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            Role roles = roleRepository.findByName("ROLE_ADMIN");
            user.setRoles((List<Role>) Collections.singleton(roles));
            userRepository.save(user);
            return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
        }
    }
}
