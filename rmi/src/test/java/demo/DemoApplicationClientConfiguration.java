package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class DemoApplicationClientConfiguration {

 @Bean
 HttpInvokerProxyFactoryBean client() {
  HttpInvokerProxyFactoryBean client = new HttpInvokerProxyFactoryBean();
  client.setServiceUrl("http://localhost:8080/messageService"); // <1>
  client.setServiceInterface(MessageService.class); // <2>
  return client;
 }
}
