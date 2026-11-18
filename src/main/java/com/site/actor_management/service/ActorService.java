package com.site.actor_management.service;

import com.site.actor_management.model.Actor;
import com.site.actor_management.repo.ActorRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ActorService {

    private final ActorRepo actorRepo;

    public ActorService(ActorRepo actorRepo) {
        this.actorRepo = actorRepo;
    }

    // READ all
    public List<Actor> getAllActors() {
        return actorRepo.findAll();
    }

    // READ one
    public Actor getActorById(Long id) {
        return actorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Actor not found with id " + id));
    }

    // CREATE
    public Actor createActor(Actor actor) {
        actor.setId(null); // make sure it is always inserted as a new row
        return actorRepo.save(actor);
    }

    // UPDATE
    public Actor updateActor(Long id, Actor updated) {
        Actor existing = getActorById(id);
        existing.setName(updated.getName());
        existing.setAge(updated.getAge());
        existing.setCountry(updated.getCountry());
        return actorRepo.save(existing);
    }

    // DELETE
    public void deleteActor(Long id) {
        if (!actorRepo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Actor not found with id " + id);
        }
        actorRepo.deleteById(id);
    }

    // SEARCH
    public List<Actor> searchByName(String name) {
        return actorRepo.findByNameContainingIgnoreCase(name);
    }
}