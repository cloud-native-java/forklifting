package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {

 @Autowired
 private GridFsTemplate gridFsTemplate;

 @Test
 public void contextLoads() {
  assertNotNull("the gridFsTemplate must not be null!", this.gridFsTemplate);
 }

}
