package com.mutant.api.controller;

import com.mutant.api.model.dto.DnaDto;
import com.mutant.api.model.dto.StatDto;
import com.mutant.api.service.DnaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DnaController {

    private DnaService dnaService;

    public DnaController(DnaService dnaService) {
        this.dnaService = dnaService;
    }

    @PostMapping("/mutant")
    public ResponseEntity<?> evaluateHuman(@RequestBody DnaDto ndaDto) {
        if(dnaService.isMutant(ndaDto.getDna())){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/stats")
    public StatDto stats() {
        return dnaService.stats();
    }
}
