package com.example.zaliczenie.controller;

import com.example.zaliczenie.model.Tea;
import com.example.zaliczenie.model.User;
import com.example.zaliczenie.repository.UserRepository;
import com.example.zaliczenie.service.TeaService;
import com.example.zaliczenie.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teas")
@RequiredArgsConstructor
public class TeaController {
    @Autowired
    private UserRepository userRepository;
    private final TeaService teaService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public List<Tea> getAllTeas() {
        return teaService.getAllTeas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tea> getTeaById(@PathVariable String id) {
        Optional<Tea> tea = teaService.getTeaById(id);
        return tea.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tea createTea(@Valid @RequestBody Tea tea, @RequestHeader("Authorization") String AuthHeader) {
        String username=jwtUtil.extractUsername(AuthHeader.substring(7));
        tea.setCreatedBy(username);
        return teaService.createTea(tea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTea(@Valid @PathVariable String id, @RequestBody Tea tea, @RequestHeader("Authorization") String authHeader) {
        String username = jwtUtil.extractUsername(authHeader.substring(7));
        Optional<Tea> existingTea = teaService.getTeaById(id);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
        }
        if (existingTea.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tea not found");
        }

        Tea teaToUpdate = existingTea.get();

        if (!teaToUpdate.getCreatedBy().equals(username) && !user.getRole().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user not owner or admin");
        }

        tea.setId(id);
        Tea updatedTea = teaService.updateTea(id, tea);
        return ResponseEntity.ok(updatedTea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTea(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        String username = jwtUtil.extractUsername(authHeader.substring(7));
        Optional<Tea> existingTea = teaService.getTeaById(id);
        if (existingTea.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tea not found");
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
        }
        Tea teaToDelete = existingTea.get();

        if (!teaToDelete.getCreatedBy().equals(username) && !user.getRole().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not owner or admin");
        }
        teaService.deleteTea(id);
        return ResponseEntity.status(200).body("Tea Deleted successfully");
    }
}
