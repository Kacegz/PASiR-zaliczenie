package com.example.zaliczenie.repository;

import com.example.zaliczenie.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByTeaId(String teaId);
    boolean existsByTeaIdAndUserId(String teaId, String userId);
    Optional<Rating> findByTeaIdAndUserId(String teaId, String userId);
}