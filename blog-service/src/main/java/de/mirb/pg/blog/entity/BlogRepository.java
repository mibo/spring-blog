package de.mirb.pg.blog.entity;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BlogRepository extends ReactiveCrudRepository<BlogEntry, String> {
  Mono<BlogEntry> findById(String id);
}
