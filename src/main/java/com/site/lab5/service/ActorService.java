package com.site.lab5.service;

import com.site.lab5.aop.LogExecutionTime;
import com.site.lab5.aop.SimpleCache;
import com.site.lab5.model.Actor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final List<Actor> actors = new ArrayList<>(List.of(
            new Actor(1L, "Daniel"),
            new Actor(2L, "Sara")
    ));

    public List<Actor> findAll() {
        return actors;
    }

    public Optional<Actor> findById(Long id) {
        return actors.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    public Actor add(Actor actor) {
        actors.add(actor);
        return actor;
    }

    public Optional<Actor> update(Long id, String name) {
        Optional<Actor> found = findById(id);
        found.ifPresent(actor -> actor.setName(name));
        return found;
    }

    public boolean deleteById(Long id) {
        return actors.removeIf(actor -> actor.getId().equals(id));
    }

    // Simulates an expensive calculation
    @SimpleCache
    @LogExecutionTime
    public int countActors() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return actors.size();
    }
}