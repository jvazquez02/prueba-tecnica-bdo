package com.bdo.pruebaTecnica.service;

import com.bdo.pruebaTecnica.model.Incidence;
import com.bdo.pruebaTecnica.repository.IncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IncidentServiceTest {
    private IncidentRepository incidentRepository;
    private IncidentService incidentService;

    @BeforeEach
    void setUp() {
        incidentRepository = mock(IncidentRepository.class);
        incidentService = new IncidentService(incidentRepository);
    }

    @Test
    void createSetsInitialStatusToOpen() {
        Incidence incidence = new Incidence();
        incidence.setTitle("VPN issue");
        incidence.setPriority(Incidence.Priority.HIGH);
        when(incidentRepository.save(any(Incidence.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Incidence result = incidentService.create(incidence);

        assertEquals(Incidence.Status.OPEN, result.getStatus());
        verify(incidentRepository).save(incidence);
    }

    @Test
    void changeStatusRejectsClosedIncidentWithConflict() {
        Incidence incidence = new Incidence();
        incidence.setId(1L);
        incidence.setStatus(Incidence.Status.CLOSED);
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(incidence));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> incidentService.changeStatus(1L, Incidence.Status.OPEN)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        verify(incidentRepository, never()).save(any(Incidence.class));
    }
}
