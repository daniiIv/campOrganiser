package com.example.camporganiser.repositories;

import com.example.camporganiser.entities.CampDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampDayRepository extends JpaRepository<CampDay, Long> {

}
