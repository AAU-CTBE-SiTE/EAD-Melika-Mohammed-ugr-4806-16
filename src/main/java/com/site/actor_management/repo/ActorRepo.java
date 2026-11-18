package com.site.actor_management.repo;

import com.site.actor_management.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepo extends JpaRepository<Actor, Long> {

    // extra query: search by name, ignoring case
    List<Actor> findByNameContainingIgnoreCase(String name);
}