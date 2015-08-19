package demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.transaction.Transactional;

// TODO map the transactional log to the filesystem. Use FUSE on Cloud Foundry to mount the filesystem.

@SpringBootApplication
public class DemoApplication {

    private Log log = LogFactory.getLog(getClass());

    @Bean
    CommandLineRunner demo(AccountService service,
                           AccountRepository repository) {
        return args -> {
            service.createAccountAndNotify("josh");
            log.info("count is " + repository.count());
            try {
                service.createAccountAndNotify("error");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            log.info("count is " + repository.count());
        };
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args).close();
    }
}

interface AccountRepository extends JpaRepository<Account, Long> {
}

@Entity
class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    Account() {
    }

    public Account(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}

@Component
class Messages {

    private Log log = LogFactory.getLog(getClass());

    @JmsListener(destination = "accounts")
    public void onMessage(String content) {
        log.info("----> " + content);
    }
}

@Service
@Transactional
class AccountService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private AccountRepository accountRepository;

    public void createAccountAndNotify(String username) {
        this.jmsTemplate.convertAndSend("accounts", username);
        this.accountRepository.save(new Account(username));
        if ("error".equals(username)) {
            throw new RuntimeException("Simulated error");
        }
    }
}