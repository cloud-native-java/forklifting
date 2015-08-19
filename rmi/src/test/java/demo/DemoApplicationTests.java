package demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DemoApplicationTests {

    private Log log = LogFactory.getLog(getClass());
    private ConfigurableApplicationContext serviceApplicationContext;

    @Before
    public void before() throws Exception {
        this.serviceApplicationContext = SpringApplication.run(DemoApplication.class); // <1>
    }

    @After
    public void tearDown() throws Exception {
        this.serviceApplicationContext.close();
    }

    @Test
    public void contextLoads() throws Exception {

        AnnotationConfigApplicationContext clientContext =
                new AnnotationConfigApplicationContext(DemoApplicationClientConfiguration.class); // <2>

        MessageService messageService = clientContext.getBean(MessageService.class); // <3>
        Message result = messageService.greet("Josh");
        assertNotNull("the result must not be null", result);
        assertEquals(result.getMessage(), "Hello, Josh!");
        log.info("result: " + result.toString());
    }

    @Configuration
    public static class DemoApplicationClientConfiguration {


        @Bean
            // <4>
        HttpInvokerProxyFactoryBean client() {
            HttpInvokerProxyFactoryBean client = new HttpInvokerProxyFactoryBean();
            client.setServiceUrl("http://localhost:8080/messageService");
            client.setServiceInterface(MessageService.class);
            return client;
        }
    }

}
