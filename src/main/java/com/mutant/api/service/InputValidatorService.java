package com.mutant.api.service;
import com.mutant.api.exception.custom.ArrayDimensionsException;
import com.mutant.api.exception.custom.InvalidNucleoidNameException;
import com.mutant.api.metric.Countable;
import com.mutant.api.model.Nucleoid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class InputValidatorService {

    private Logger logger = LoggerFactory.getLogger(InputValidatorService.class);

    public void validate(String[] dna) {
        validateNucleoidsAreValid(dna);
        validateMatrixIsSquare(dna);
    }

    public InputValidatorService() {
    }

    private void validateNucleoidsAreValid(String[] dna) {
        try {
            Arrays.stream(dna)
                    .flatMap(it -> it.chars().mapToObj(e -> (char) e)).collect(Collectors.toList())
                    .forEach(it -> Nucleoid.valueOf(it.toString()));
        } catch (IllegalArgumentException e) {
            logger.error("DNA's nucleoid must be one of A, T, C, G");
            throw new InvalidNucleoidNameException();
        }
    }

    @Countable(metricName = "counter.test")
    public void validateMatrixIsSquare(String[] dna) {
        int arrayLength = dna.length;
        if (Arrays.stream(dna).anyMatch(it -> it.length() != arrayLength))
            throw new ArrayDimensionsException();
    }
}
