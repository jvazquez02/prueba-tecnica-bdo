package com.bdo.pruebaTecnica.repository;

import com.bdo.pruebaTecnica.model.Incidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incidence, Long> {

}
