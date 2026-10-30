package com.bdo.pruebaTecnica.service;

import com.bdo.pruebaTecnica.model.Incidence;
import com.bdo.pruebaTecnica.repository.IncidentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    /**
     * Validates the incident and saves it with its initial status
     */
    public Incidence create(Incidence incidence) {
        if (incidence.getTitle() == null || incidence.getTitle().isBlank() || incidence.getTitle().length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required and must be at most 100 characters.");
        }
        if (incidence.getPriority() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Priority is required.");
        }
        incidence.setId(null);
        incidence.setStatus(Incidence.Status.OPEN);
        return incidentRepository.save(incidence);
    }

    /**
     * Returns all incidents, optionally filtered by status
     */
    public List<Incidence> findAll(Incidence.Status status) {
        if (status == null) {
            return incidentRepository.findAll();
        }
        return incidentRepository.findByStatus(status);
    }

    /**
     * Changes an incident's status unless it does not exist or is already close
     */
    public Incidence changeStatus(long id, Incidence.Status newStatus) {
        if (newStatus == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required.");
        }
        Incidence incidence = incidentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incident was not found."));
        if (incidence.getStatus() == Incidence.Status.CLOSED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Closed incidents cannot be reopened.");
        }
        incidence.setStatus(newStatus);
        return incidentRepository.save(incidence);
    }
}