package com.sid.tutorials.springboot.batch.repository;

import com.sid.tutorials.springboot.batch.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kunmu On 15-08-2024
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
}
