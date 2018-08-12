package de.mirb.pg.blog.control;

import de.mirb.pg.blog.entity.BlogEntry;
import de.mirb.pg.blog.entity.BlogRepository;
import org.junit.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class BlogServiceTest {

  @Test
  public void createAndGetEntry() {
    BlogRepository mockedRepo = Mockito.mock(BlogRepository.class);
    Mockito.when(mockedRepo.save(Mockito.any(BlogEntry.class))).thenReturn(
        Mono.just(BlogEntry.createBy("mibo").content("some content").create()));
    Mockito.when(mockedRepo.findAll()).thenReturn(
        Flux.just(BlogEntry.createBy("mibo").content("some content").create()));
    BlogService service = new BlogService(mockedRepo);
    //
    service.createEntry("mibo", "some content");
    //
    assertEquals(1, service.getAllEntries().size());
    BlogEntry entry = service.getAllEntries().iterator().next();
    assertThat(entry.getCreatedBy(), is("mibo"));
    assertThat(entry.getContent(), is("some content"));
  }

  @Test
  public void createEntry() {
    BlogRepository mockedRepo = Mockito.mock(BlogRepository.class);
    BlogEntry e = BlogEntry.createBy("mibo").content("some content").create();
    Mockito.when(mockedRepo.save(Mockito.refEq(e, "id", "createdAt"))).thenReturn(Mono.just(e));
    BlogService service = new BlogService(mockedRepo);
    //
    BlogEntry created = service.createEntry("mibo", "some content");
    assertThat(created.getCreatedBy(), is("mibo"));
    assertThat(created.getContent(), is("some content"));
  }


  @Test
  public void updateEntry() {
    BlogRepository mockedRepo = Mockito.mock(BlogRepository.class);
    BlogEntry created = BlogEntry.createBy("mibo").content("some content").create();
    BlogEntry updated = BlogEntry.createBy("mibo").content("another content").create();
    Mockito.when(mockedRepo.save(Mockito.refEq(created, "id", "createdAt"))).thenReturn(Mono.just(created));
    Mockito.when(mockedRepo.save(Mockito.refEq(updated, "id", "createdAt"))).thenReturn(Mono.just(updated));
    Mockito.when(mockedRepo.findAll()).thenReturn(Flux.just(updated));
    Mockito.when(mockedRepo.findById(created.getId())).thenReturn(Mono.just(updated));

    BlogService service = new BlogService(mockedRepo);
    //
    BlogEntry c = service.createEntry("mibo", "some content");
    service.updateEntry(c.getId(), "another content");

    //
    assertEquals(1, service.getAllEntries().size());
    BlogEntry entry = service.getAllEntries().iterator().next();
    assertThat(entry.getCreatedBy(), is("mibo"));
    assertThat(entry.getContent(), is("another content"));
  }
}
