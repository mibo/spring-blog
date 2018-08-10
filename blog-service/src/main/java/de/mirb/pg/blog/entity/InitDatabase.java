package de.mirb.pg.blog.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class InitDatabase {
  private static final Logger LOG = LoggerFactory.getLogger(InitDatabase.class);

  @Bean
  CommandLineRunner init(MongoOperations operations) {
    return args -> {
      operations.dropCollection(BlogEntry.class);

      operations.insert(BlogEntry.createBy("mibo").content("Test data one.").create());
      operations.insert(BlogEntry.createBy("mibo").content("Test data two.").create());

      operations.findAll(BlogEntry.class).forEach(entry -> {
        LOG.debug("Found entry: {}", entry);
      });
    };
  }
}