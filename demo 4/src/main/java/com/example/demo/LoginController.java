package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*") // allow CORS for testing; in production restrict origins
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (req == null || req.getUsername() == null || req.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Missing username or password"));
        }

        if ("alice".equals(req.getUsername()) && "secret".equals(req.getPassword())) {
            // generate a simple token (UUID) for demo purposes
            String token = UUID.randomUUID().toString();
            return ResponseEntity.ok(new LoginResponse("success", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials"));
        }
    }
}