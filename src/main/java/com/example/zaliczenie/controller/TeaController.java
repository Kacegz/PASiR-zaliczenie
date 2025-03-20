package com.example.zaliczenie.controller;

import com.example.zaliczenie.model.Tea;
import com.example.zaliczenie.service.TeaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teas")
@RequiredArgsConstructor
public class TeaController {

    private final TeaService teaService;

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
    public Tea createTea(@RequestBody Tea tea) {
        return teaService.createTea(tea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tea> updateTea(@PathVariable String id, @RequestBody Tea tea) {
        try {
            return ResponseEntity.ok(teaService.updateTea(id, tea));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTea(@PathVariable String id) {
        teaService.deleteTea(id);
        return ResponseEntity.noContent().build();
    }
}
