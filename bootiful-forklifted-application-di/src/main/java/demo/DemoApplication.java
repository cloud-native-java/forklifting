package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@SpringBootApplication
public class DemoApplication {

    @Bean
    CommandLineRunner jdbc(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String pw,
            JdbcTemplate template) {
        return args -> {

            System.out.println("url: '" + url+"'");
            System.out.println("username: '" + username+"'");
            System.out.println("password: '" + pw+"'");

            RowMapper<FtpUser> rowMapper = (rs, i) ->
                    new FtpUser(rs.getString("USERNAME"), rs.getBoolean("ADMIN"), rs.getBoolean("ENABLED"));

            template.query(" SELECT * FROM FTP_USER ", rowMapper).forEach(System.out::println);
        };

    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

class FtpUser {
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FtpUser{");
        sb.append("username='").append(username).append('\'');
        sb.append(", admin=").append(admin);
        sb.append(", enabled=").append(enabled);
        sb.append('}');
        return sb.toString();
    }

    private String username;
    private boolean admin, enabled;

    public FtpUser(String username, boolean admin, boolean enabled) {
        this.username = username;
        this.admin = admin;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isEnabled() {
        return enabled;
    }
}