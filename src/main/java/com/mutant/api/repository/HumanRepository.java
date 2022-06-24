package com.mutant.api.repository;

import com.mutant.api.model.Human;
import com.mutant.api.model.dto.StatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HumanRepository extends JpaRepository<Human,Long> {

    Human findByDnaHash(String dnaHash);

    @Query(value = "SELECT SUM(is_mutant = true) as count_mutant_dna, SUM(is_mutant = false) as count_human_dna, " +
            "      ROUND((SUM(is_mutant = true) * 100.0) / (COUNT(id) * 100.0), 2) as ratio  FROM HUMAN ",nativeQuery = true)
    StatDto getStatsNative();


}
