package com.site.lab5.controller;

import com.site.lab5.aop.Audited;
import com.site.lab5.aop.LogExecutionTime;
import com.site.lab5.model.Actor;
import com.site.lab5.service.ActorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @LogExecutionTime
    @GetMapping
    public List<Actor> getAllActors() {
        return actorService.findAll();
    }

    @GetMapping("/count")
    public int countActors() {
        return actorService.countActors();
    }

    @Audited(action = "READ_ONE")
    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        return actorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @LogExecutionTime
    @PostMapping
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        if (actorService.existsById(actor.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        actorService.add(actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(actor);
    }

    @GetMapping("/error-test")
    public Actor errorTest() {
        throw new IllegalStateException("Demo exception for AOP");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor updatedActor) {
        return actorService.update(id, updatedActor.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActorById(@PathVariable Long id) {
        if (actorService.deleteById(id)) {
            return ResponseEntity.ok("Actor deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Actor not found");
    }
}