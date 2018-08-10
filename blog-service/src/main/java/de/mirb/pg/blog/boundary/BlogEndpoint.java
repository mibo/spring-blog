package de.mirb.pg.blog.boundary;

import de.mirb.pg.blog.control.BlogService;
import de.mirb.pg.blog.entity.BlogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/blog")
public class BlogEndpoint {
  private static final Logger LOG = LoggerFactory.getLogger(BlogEndpoint.class);

  private BlogService blogService;

  public BlogEndpoint(BlogService blogService) {
    this.blogService = blogService;
  }

  @PostMapping(value = "/entry",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BlogEntry> createEntry(@RequestBody BlogRequestEntry reqEntry) {
    LOG.debug("Create entry...");
    BlogEntry blogEntry = blogService.createEntry(reqEntry.getUsername(), reqEntry.getContent());
    return ResponseEntity.created(URI.create("/blog/entry/" + blogEntry.getId())).body(blogEntry);
  }
//  @PostMapping(value = "/entry",
//      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<BlogEntry> createEntry(@RequestBody String username, @RequestBody String content) {
//    BlogEntry blogEntry = blogService.createEntry(username, content);
//    return ResponseEntity.created(URI.create("/blog/entry/" + blogEntry.getId())).body(blogEntry);
//  }

  @GetMapping(value = "/entry", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Collection<BlogEntry>> listAllEntries() {
    LOG.debug("Get all entries...");
    Collection<BlogEntry> entries = blogService.getAllEntries();
    return ResponseEntity.ok(entries);
  }

  @GetMapping(value = "/entry/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BlogEntry> getSingleEntry(@PathVariable("id") String id) {
    LOG.debug("Get entry with id {}...", id);
    Optional<BlogEntry> entry = blogService.getById(id);
    return entry
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
