package finalproject.server.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import finalproject.server.security.util.JwtUtil;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
        HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = req.getHeader("Authorization");

        String username = null;
        String jwt = null;

        System.out.println("JwtRequestFilter step 1");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println("jwt" +jwt);

            username = jwtUtil.getUsernameFromToken(jwt);
            System.out.println("username" +username);
        }

        System.out.println("JwtRequestFilter step 2");
        if (username != null && 
            SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = new User(
                username, "placeholder", new ArrayList<>());

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
                usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(
                    usernamePasswordAuthenticationToken);
                System.out.println("securitycontext success!");
            } else {
                System.out.println("validateToken fail!");
                resp.setStatus(403);
                resp.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
                try (PrintWriter pw = resp.getWriter()) {
                    JsonObject payload = Json.createObjectBuilder()
                        .add("error", "Invalid token")
                        .build();
                    pw.print(payload.toString());
                }
            }
        }

        System.out.println("JwtRequestFilter step final - chain");
        chain.doFilter(req, resp);
    }
    
}
