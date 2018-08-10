package de.mirb.pg.blog.control;

import de.mirb.pg.blog.entity.BlogEntry;
import de.mirb.pg.blog.entity.BlogRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BlogService {

  private Map<String, BlogEntry> blogEntries = new HashMap<>();
//  private final AtomicInteger lastId = new AtomicInteger(0);
  private BlogRepository repository;

  public BlogService(BlogRepository repository) {
    this.repository = repository;
  }

  public BlogEntry createEntry(String username, String content) {
    BlogEntry entry = BlogEntry.createBy(username)
        .content(content)
        .create();
    Mono<BlogEntry> created = repository.save(entry);
    return created.block();
  }

  public Optional<BlogEntry> updateEntry(String id, String content) {
    Optional<BlogEntry> found = repository.findById(id).blockOptional();
    return found.map(blogEntry ->
      repository.save(
          BlogEntry.from(blogEntry).content(content).create()).block());

    //    Mono<Void> updated = Mono.when(found.compose(e ->
//        Mono.just(repository.save(
//            BlogEntry.from(e.block()).content(content).create()))));
  }

  public Optional<BlogEntry> getById(String id) {
    return repository.findById(id).blockOptional();
  }

  public Collection<BlogEntry> getAllEntries() {
    return repository.findAll().collectList().block();
  }
}
