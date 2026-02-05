package com.seplag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seplag.model.Regional;
import com.seplag.service.RegionalImportService;
import com.seplag.service.RegionalService;

@RestController
@RequestMapping("/api/v1/regionais")
public class RegionalController {
	
	@Autowired
    private RegionalImportService regionalImportService;
	
	@Autowired
	private RegionalService regionalService;
   

    @PostMapping("/importar")
    public ResponseEntity<String> importar() {
        regionalImportService.sincronizarRegionais();
        return ResponseEntity.ok("Regionais sincronizadas com sucesso!");
    }
    
    
    @GetMapping
    public ResponseEntity<List<Regional>> listarTodas() {
        return ResponseEntity.ok(regionalService.listarAtivas());
    }

}