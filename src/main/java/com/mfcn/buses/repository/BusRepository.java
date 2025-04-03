package com.mfcn.buses.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfcn.buses.model.Bus;

public interface BusRepository extends JpaRepository<Bus, Long>{

}
