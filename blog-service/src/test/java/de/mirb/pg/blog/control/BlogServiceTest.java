package de.mirb.pg.blog.control;

import de.mirb.pg.blog.entity.BlogEntry;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class BlogServiceTest {

  @Test
  public void createEntry() {
    BlogService service = new BlogService();
    //
    service.createEntry("mibo", "some content");
    //
    assertEquals(1, service.getAllEntries().size());
    BlogEntry entry = service.getAllEntries().iterator().next();
    assertThat(entry.getCreatedBy(), is("mibo"));
    assertThat(entry.getContent(), is("some content"));
  }


  @Test
  public void updateEntry() {
    BlogService service = new BlogService();
    //
    BlogEntry created = service.createEntry("mibo", "some content");
    service.updateEntry(created.getId(), "another content");

    //
    assertEquals(1, service.getAllEntries().size());
    BlogEntry entry = service.getAllEntries().iterator().next();
    assertThat(entry.getCreatedBy(), is("mibo"));
    assertThat(entry.getContent(), is("another content"));
  }
}
