package demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private AccountService service;

	@Autowired
	private AccountRepository repository;

	@Test
	public void contextLoads() {
		service.createAccountAndNotify("josh");
		log.info("count is " + repository.count());
		try {
			service.createAccountAndNotify("error");
		}
		catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.info("count is " + repository.count());
		assertEquals(repository.count(), 1);
	}
}