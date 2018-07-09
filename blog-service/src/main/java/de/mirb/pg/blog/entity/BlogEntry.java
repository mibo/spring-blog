package de.mirb.pg.blog.entity;

import org.springframework.lang.NonNull;

import java.util.Date;

public class BlogEntry {
  private final String id;
  private Date createdAt;
  private String createdBy;
  private String content;

//  private BlogEntry(@NonNull String id, Date createdAt, String createdBy, String content) {
//    this.id = id;
//    this.createdAt = createdAt;
//    this.createdBy = createdBy;
//    this.content = content;
//  }

  private BlogEntry(@NonNull String id) {
    this.id = id;
  }

  public static BlogEntry.Builder with(String id) {
    return new BlogEntry.Builder(id);
  }

  public static BlogEntry.Builder from(BlogEntry be) {
    return new BlogEntry.Builder(be);
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  private void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  private void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getContent() {
    return content;
  }

  private void setContent(String content) {
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public static class Builder {
    BlogEntry be;

    Builder(String id) {
      be = new BlogEntry(id);
    }

    Builder(BlogEntry entry) {
      be = new BlogEntry(entry.id);
      be.content = entry.content;
      be.createdBy = entry.createdBy;
      be.createdAt = entry.createdAt;
    }

    public Builder createdAt(Date date) {
      be.setCreatedAt(date);
      return this;
    }
    public Builder createdBy(String username) {
      be.setCreatedBy(username);
      return this;
    }
    public Builder content(String content) {
      be.setContent(content);
      return this;
    }

    public BlogEntry create() {
      return be;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BlogEntry blogEntry = (BlogEntry) o;

    if (!id.equals(blogEntry.id)) return false;
    if (createdAt != null ? !createdAt.equals(blogEntry.createdAt) : blogEntry.createdAt != null) return false;
    if (createdBy != null ? !createdBy.equals(blogEntry.createdBy) : blogEntry.createdBy != null) return false;
    return content != null ? content.equals(blogEntry.content) : blogEntry.content == null;
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
    result = 31 * result + (content != null ? content.hashCode() : 0);
    return result;
  }
}
