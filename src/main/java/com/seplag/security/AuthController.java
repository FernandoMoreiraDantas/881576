package com.seplag.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> dados) {
        // Em um sistema real, você verificaria o usuário/senha no banco aqui
        String usuario = dados.get("login");
        
        if ("admin".equals(usuario)) {
            String token = tokenService.gerarToken(usuario);
            return ResponseEntity.ok(Map.of("token", token));
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
