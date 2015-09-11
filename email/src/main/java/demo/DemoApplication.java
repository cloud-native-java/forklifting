package demo;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@RestController
class EmailRestController {

    @Autowired
    private SendGrid sendGrid;

    // <1>
    @RequestMapping("/email")
    SendGrid.Response email(@RequestParam String message) throws Exception {
        SendGrid.Email email = new SendGrid.Email();
        email.setHtml("<hi>" + message + "</h1>");
        email.setText(message);
        email.setTo(new String[]{"user1@host.io"});
        email.setToName(new String[]{"Josh"});
        email.setFrom("user2@host.io");
        email.setFromName("Josh (sender)");
        email.setSubject("I just called.. to say.. I (message truncated)");
        return sendGrid.send(email);
    }

}