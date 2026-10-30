package com.bdo.pruebaTecnica.controller;

import com.bdo.pruebaTecnica.model.Incidence;
import com.bdo.pruebaTecnica.service.IncidentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService service;

    public IncidentController(IncidentService service) {
        this.service = service;
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Incidence create(@RequestBody Incidence incidence) {
        return service.create(incidence);
    }

    @GetMapping("/findAll")
    public List<Incidence> findAll(@RequestParam(required = false) Incidence.Status status) {
        return service.findAll(status);
    }

    @PatchMapping("/{id}/status")
    public Incidence changeStatus(@PathVariable long id,
                                  @RequestBody StatusRequest request) {
        return service.changeStatus(id, request.status());
    }

    public record StatusRequest(Incidence.Status status) { }
}