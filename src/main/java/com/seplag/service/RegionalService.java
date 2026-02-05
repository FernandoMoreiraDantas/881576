package com.seplag.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.seplag.model.Regional;
import com.seplag.repository.RegionalRepository;

@Service
public class RegionalService {

    private final RegionalRepository regionalRepository;

    public RegionalService(RegionalRepository regionalRepository) {
        this.regionalRepository = regionalRepository;
    }

    public List<Regional> listarAtivas() {
        return regionalRepository.findByAtivoTrue();
    }
}
