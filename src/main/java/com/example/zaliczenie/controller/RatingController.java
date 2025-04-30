package com.example.zaliczenie.controller;

import com.example.zaliczenie.model.Rating;
import com.example.zaliczenie.model.Tea;
import com.example.zaliczenie.model.User;
import com.example.zaliczenie.repository.RatingRepository;
import com.example.zaliczenie.repository.TeaRepository;
import com.example.zaliczenie.repository.UserRepository;
import com.example.zaliczenie.service.TeaService;
import com.example.zaliczenie.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teas")
@RequiredArgsConstructor
public class RatingController {
    private final RatingRepository ratingRepository;
    private final TeaRepository teaRepository;
    private final UserRepository userRepository;
    private final TeaService teaService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{id}/rate")
    public ResponseEntity<?> addRating(@PathVariable String id,@Valid @RequestBody Rating rating, @RequestHeader("Authorization") String authHeader) {
        String username = jwtUtil.extractUsername(authHeader.substring(7));
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
        }

        Tea tea = teaRepository.findById(id).orElse(null);
        if (tea == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tea not found");
        }

        if (ratingRepository.existsByTeaIdAndUserId(id, user.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has already rated this tea");
        }

        rating.setTeaId(id);
        rating.setUserId(user.getId());
        ratingRepository.save(rating);
        teaService.updateAverageRating(id);

        return ResponseEntity.status(HttpStatus.CREATED).body("Rating added successfully");
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<List<Rating>> getRatings(@PathVariable String id) {
        List<Rating> ratings = ratingRepository.findByTeaId(id);
        return ResponseEntity.ok(ratings);
    }
}