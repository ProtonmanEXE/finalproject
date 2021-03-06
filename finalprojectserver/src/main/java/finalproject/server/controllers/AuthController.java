package finalproject.server.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finalproject.server.models.AuthenticationRequest;
import finalproject.server.models.AuthenticationResponse;
import finalproject.server.security.util.JwtUtil;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class AuthController {
    
    @Autowired
    private AuthenticationManager auMgr;

    @Autowired
    private JwtUtil jwtUtil;

    // intercept route with authenticate and check against user db (MySQL)
    @PostMapping(path="/authenticate")
    public ResponseEntity<?> createAuToken(
        @RequestBody AuthenticationRequest auRequest) throws Exception {

        // authenticate user name and password
        try {
            auMgr.authenticate(new UsernamePasswordAuthenticationToken(
                auRequest.getUserName(), auRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(
                "Incorrect username and/or password");
        } 
        
        // create UserDetail Object
        final UserDetails userDetails = new User(
            auRequest.getUserName(), auRequest.getPassword(), new ArrayList<>());

        // user UserDetail Object to generate token
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));        
    }

}
