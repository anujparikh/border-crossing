package org.example.persistence.repository;

import org.example.persistence.model.Crossing;
import org.example.persistence.model.CrossingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrossingRepository extends JpaRepository<Crossing, Long> { }
