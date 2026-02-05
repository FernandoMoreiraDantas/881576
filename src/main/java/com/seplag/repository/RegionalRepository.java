package com.seplag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seplag.model.Regional;

@Repository
public interface RegionalRepository extends JpaRepository<Regional, Integer> {
	List<Regional> findByAtivoTrue();
}
