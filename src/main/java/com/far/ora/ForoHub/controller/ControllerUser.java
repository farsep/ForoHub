package com.far.ora.ForoHub.controller;

import com.far.ora.ForoHub.Repository.IUserRepo;
import com.far.ora.ForoHub.models.User;
import com.far.ora.ForoHub.models.dto.login.LoginUser;
import com.far.ora.ForoHub.models.dto.register.RegisterUser;
import com.far.ora.ForoHub.models.dto.show.ShowUser;
import com.far.ora.ForoHub.models.dto.update.UpdateUser;
import com.far.ora.ForoHub.security.TokenService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.PasswordAuthentication;
import java.net.URI;

@RestController
@RequestMapping("/user")
public class ControllerUser {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUserRepo userRepo;

    @GetMapping("/{id}")
    public ResponseEntity<ShowUser> getUser(@PathVariable Long id) {
        User user = userRepo.findById(id).orElseThrow();

        return ResponseEntity.ok(new ShowUser(user));
    }

    @GetMapping
    public ResponseEntity<Page<ShowUser>> getAllUsers(Pageable pageable) {
        Page<ShowUser> users = userRepo.findAll(pageable).map(ShowUser::new);
        return ResponseEntity.ok(users);
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUser user) {
        String password = passwordEncoder.encode(user.password());
        User user1 = new User(user, password);
        userRepo.save(user1);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user1.getId())
                .toUri();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "User created successfully");

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        String JWTtoken = tokenService.generateToken(user1);
        jsonObject.addProperty("token", JWTtoken);
        return ResponseEntity.created(location).body(jsonObject.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUser user) {
        Authentication internalAuthentication = new UsernamePasswordAuthenticationToken(user.username(), user.password());
        authenticationManager.authenticate(internalAuthentication);
        User user1 = (User) userRepo.findByUsername(user.username());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "User logged in successfully");
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        String JWTtoken = tokenService.generateToken(user1);
        jsonObject.addProperty("token", JWTtoken);
        return ResponseEntity.ok(jsonObject.toString());
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ShowUser> updateUser(@PathVariable Long id, @RequestBody UpdateUser user) {
        User user1 = userRepo.findById(id).orElseThrow();
        user1.Update(user);
        return ResponseEntity.ok(new ShowUser(user1));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "User deleted successfully");

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        return ResponseEntity.noContent().headers(header).build();
    }

}
