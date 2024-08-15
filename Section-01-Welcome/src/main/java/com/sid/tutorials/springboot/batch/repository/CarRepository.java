package com.sid.tutorials.springboot.batch.repository;

import com.sid.tutorials.springboot.batch.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kunmu On 15-08-2024
 */
public interface CarRepository extends JpaRepository<CarEntity, Integer> {
}
