package com.mutant.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface StatDto {

  @JsonProperty("count_mutant_dna")
  Long getcount_mutant_dna();

  @JsonProperty("count_human_dna")
  Long getcount_human_dna();

  @JsonProperty("ratio")
  Double getratio();
}