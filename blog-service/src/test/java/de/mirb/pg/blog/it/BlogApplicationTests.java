package de.mirb.pg.blog.it;

import de.mirb.pg.blog.boundary.BlogRequestEntry;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
//@DataMongoTest
public class BlogApplicationTests {


  // see => https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-with-running-server
  // this setup requires spring-webflux on the classpath. If you can’t or won’t add webflux, Spring Boot also provides a TestRestTemplate facility:
//  @Autowired WebTestClient client;
  @Autowired
  private TestRestTemplate restTemplate;


	@Test
	public void contextLoads() {
	}

	@Test
	public void readEntries() {
    ResponseEntity<Collection> entity = restTemplate.getForEntity("/blog/entry", Collection.class);
    assertThat(entity.getStatusCodeValue(), Matchers.is(200));
    assertThat("Got response...", entity.getStatusCode().is2xxSuccessful());
	}

	@Test
	public void createAndReadEntries() {
    BlogRequestEntry e = new BlogRequestEntry();
    e.setContent("content");
    e.setUsername("mibo");
    ResponseEntity<String> postResponse = restTemplate.postForEntity("/blog/entry", e, String.class);
    assertThat(postResponse.getStatusCodeValue(), Matchers.is(201));
//    System.out.println(postResponse.getBody());
    assertThat(postResponse.getBody(), containsString("content"));
    assertThat(postResponse.getBody(), containsString("mibo"));

    ResponseEntity<Collection> getResponse = restTemplate.getForEntity("/blog/entry", Collection.class);
    assertThat(getResponse.getStatusCodeValue(), Matchers.is(200));
    assertThat("Got response...", getResponse.getStatusCode().is2xxSuccessful());
//    System.out.println(getResponse.getBody());
    Collection body = getResponse.getBody();
    assertThat(body, notNullValue());
    assertThat(body.size(), greaterThan(0));
//    assertThat(getResponse.getBody(), containsString("mibo"));
	}

}
