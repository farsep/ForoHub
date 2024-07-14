package com.far.ora.ForoHub.security;

import com.far.ora.ForoHub.Repository.IUserRepo;
import com.far.ora.ForoHub.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class PreFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Filtering request");
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            System.out.println("Authorization Header: " + authorizationHeader);
            String token = authorizationHeader.replace("Bearer ", "");
            System.out.println("Token: " + token);
            String UserName = tokenService.getSubjectAndVerify(token);
            if (UserName != null) {
                System.out.println("User Name: " + UserName);

                var User = userRepo.findByUsername(UserName);
                //check if authorization is the same as the "ROLE_JUAN" in the database
                if (User.getAuthorities().toString().contains("ROLE_USER")) {
                    var authentication = new UsernamePasswordAuthenticationToken(User, null, Collections.singleton(new SimpleGrantedAuthority("THE_USER")));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        }
        System.out.println("Filtering response");
        filterChain.doFilter(request, response);
    }
}
