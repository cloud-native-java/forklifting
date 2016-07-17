package demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
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
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		log.info("count is " + repository.count());
		assertEquals(repository.count(), 1);
	}
}
