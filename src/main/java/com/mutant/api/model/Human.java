package com.mutant.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Setter
@Entity
public class Human {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private boolean isMutant;
    @Column
    private String dnaHash;

    public Human() {
    }

    public Human(String dnaHash) {
        this.dnaHash = dnaHash;
        this.isMutant = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(dnaHash, human.dnaHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dnaHash);
    }
}
