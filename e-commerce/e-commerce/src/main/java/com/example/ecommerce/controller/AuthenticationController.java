package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AuthenticationRequest;
import com.example.ecommerce.dto.AuthenticationResponse;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.user.UserService;
import com.example.ecommerce.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization ";

    @PostMapping("/authenticate")

    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException, JSONException, ServletException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "User is not activated");
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());
        return new AuthenticationResponse(jwt);
    }
}
