package com.mutant.api.service;

import com.mutant.api.model.Human;
import com.mutant.api.model.dto.StatDto;
import com.mutant.api.repository.HumanRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DnaService {

    @Autowired
    private HumanRepository humanRepository;
    private TraverseService traverseService;
    private InputValidatorService inputValidatorService;

    public DnaService(HumanRepository humanRepository, TraverseService traverseService, InputValidatorService inputValidatorService) {
        this.humanRepository = humanRepository;
        this.traverseService = traverseService;
        this.inputValidatorService = inputValidatorService;
    }

    @Cacheable(cacheNames = "dna")
    public boolean isMutant(String[] dna){
        String dnaHash = doHashDna(dna);
        Human human = humanRepository.findByDnaHash(dnaHash);

        if (human != null) {
            return human.isMutant();
        }
        inputValidatorService.validate(dna);

        human = new Human(dnaHash);
        if (traverseService.findMutant(dna) > 1) {
            human.setMutant(true);
        }

        humanRepository.save(human);


        return human.isMutant();
    }

    public StatDto stats() {
        return humanRepository.getStatsNative();
    }

    private String doHashDna(String[] dna) {
        return DigestUtils.md5Hex(Arrays.toString(dna));
    }
}
