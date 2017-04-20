package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {

 @Autowired
 private DataSource dataSource;

 @Autowired
 private JdbcTemplate jdbcTemplate;

 @Test
 public void contextLoads() {
  assertNotNull("jdbcTemplate must not be null!", this.jdbcTemplate);
  assertNotNull("dataSource must not be null!", this.dataSource);
 }

}
