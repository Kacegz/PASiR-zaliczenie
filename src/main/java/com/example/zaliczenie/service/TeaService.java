package com.example.zaliczenie.service;

import com.example.zaliczenie.model.Tea;
import com.example.zaliczenie.repository.TeaRepository;
import com.example.zaliczenie.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeaService {
    @Autowired
    private TeaRepository teaRepository;

    public List<Tea> getAllTeas() {
        return teaRepository.findAll();
    }

    public Optional<Tea> getTeaById(String id) {
        return teaRepository.findById(id);
    }

    public Tea createTea(Tea tea) {
        return teaRepository.save(tea);
    }

    public Tea updateTea(String id, Tea teaDetails) {
        Tea tea = teaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tea not found"));
        tea.setName(teaDetails.getName());
        tea.setDescription(teaDetails.getDescription());
        tea.setOrigin(teaDetails.getOrigin());
        return teaRepository.save(tea);
    }

    public void deleteTea(String id) {
        Tea tea = teaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tea not found"));
        teaRepository.delete(tea);
    }
}
