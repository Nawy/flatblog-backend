package com.ermolaev.flatblog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableReactiveMongoRepositories
public class FlatblogApplicationTests {

  @Test
  public void contextLoads() {
  }

}
