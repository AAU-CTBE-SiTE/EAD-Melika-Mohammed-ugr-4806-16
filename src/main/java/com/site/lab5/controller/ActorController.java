package com.site.lab5.controller;

import com.site.lab5.model.Actor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private final List<Actor> actors = new ArrayList<>(List.of(
            new Actor(1L, "Daniel"),
            new Actor(2L, "Sara")
    ));

    @GetMapping
    public List<Actor> getAllActors() {
        return actors;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        for (Actor actor : actors) {
            if (actor.getId().equals(id)) {
                return ResponseEntity.ok(actor);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        for (Actor existing : actors) {
            if (existing.getId().equals(actor.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        actors.add(actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(actor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor updatedActor) {
        for (Actor actor : actors) {
            if (actor.getId().equals(id)) {
                actor.setName(updatedActor.getName());
                return ResponseEntity.ok(actor);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActorById(@PathVariable Long id) {
        boolean removed = actors.removeIf(actor -> actor.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok("Actor deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Actor not found");
    }
}