package de.mirb.pg.blog.control;

import de.mirb.pg.blog.entity.BlogEntry;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BlogService {

  private Map<String, BlogEntry> blogEntries = new HashMap<>();
  private final AtomicInteger lastId = new AtomicInteger(0);

  public BlogEntry createEntry(String username, String content) {
    BlogEntry entry = BlogEntry.with(getNextId())
        .createdAt(new Date())
        .createdBy(username)
        .content(content)
        .create();
    blogEntries.put(entry.getId(), entry);
    return entry;
  }

  public BlogEntry updateEntry(String id, String content) {
    BlogEntry be = blogEntries.get(id);
    BlogEntry updated = BlogEntry.from(be).content(content).create();
    blogEntries.put(id, updated);
    return updated;
  }

  public Collection<BlogEntry> getAllEntries() {
    return Collections.unmodifiableCollection(blogEntries.values());
  }

  private String getNextId() {
    return String.valueOf(lastId.incrementAndGet());
  }
}
