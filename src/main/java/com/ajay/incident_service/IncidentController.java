package com.ajay.incident_service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/incidents")
public class IncidentController {

    record CreateRequest(@NotBlank String title, @NotNull Incident.Severity severity) {}
    record StatusRequest(@NotNull Incident.Status status) {}

    private final IncidentRepository repo;

    public IncidentController(IncidentRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Incident create(@Valid @RequestBody CreateRequest req) {
        Incident i = new Incident();
        i.setTitle(req.title());
        i.setSeverity(req.severity());
        return repo.save(i);
    }

    @GetMapping
    public List<Incident> list() {
        return repo.findAll();
    }

    @PatchMapping("/{id}")
    public Incident updateStatus(@PathVariable Long id, @Valid @RequestBody StatusRequest req) {
        Incident i = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        i.setStatus(req.status());
        i.setResolvedAt(req.status() == Incident.Status.RESOLVED ? Instant.now() : null);
        return repo.save(i);
    }
}