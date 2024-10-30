package com.example.thiago_project.repository;

import com.example.thiago_project.models.Musicas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicasRepository extends JpaRepository<Musicas, Integer> {

}
