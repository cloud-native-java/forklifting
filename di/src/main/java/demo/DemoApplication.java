package demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.ResultSet;

@SpringBootApplication
public class DemoApplication {

 private Log log = LogFactory.getLog(getClass());

 public static void main(String[] args) {
  SpringApplication.run(DemoApplication.class, args);
 }

 @Bean
 DataSource h2(@Value("${spring.datasource.url}") String url,
  @Value("${spring.datasource.username}") String username,
  @Value("${spring.datasource.password}") String pw) {

  log.info(String.format(
   "creating an embedded datasource, but we could as easily"
    + " have actually constructed a %s pointing to a real database",
   javax.sql.DataSource.class.getName()));

  log.info(String.format("\turl: '%s', username: '%s', password: '%s'", url,
   username, pw));

  return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
 }

 @Bean
 CommandLineRunner jdbc(JdbcTemplate template) {
  return args -> template.query("select * from FTP_USER", (ResultSet rs) -> log
   .info(String.format("username: %s, admin?: %s, enabled?: %s",
    rs.getString("USERNAME"), rs.getString("ADMIN"), rs.getString("ENABLED"))));

 }
}