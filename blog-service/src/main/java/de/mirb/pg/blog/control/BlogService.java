package de.mirb.pg.blog.control;

import de.mirb.pg.blog.entity.BlogEntry;
import de.mirb.pg.blog.entity.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

  public BlogEntry updateEntry(String id, String content) {
//    BlogEntry be = blogEntries.get(id);
    Mono<BlogEntry> found = repository.findById(id);
    found.blockOptional().ifPresent(e -> {
        repository.save(BlogEntry.from(e).content(content).create());
      }
    );
    return null;
  }

  public Collection<BlogEntry> getAllEntries() {
    return repository.findAll().collectList().block();
  }
}
