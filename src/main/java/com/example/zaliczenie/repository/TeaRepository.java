package com.example.zaliczenie.repository;

import com.example.zaliczenie.model.Tea;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeaRepository extends MongoRepository<Tea, String> {

}