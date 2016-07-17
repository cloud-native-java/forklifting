package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	MessageService messageService() { // <1>
		return new SimpleMessageService();
	}

	@Bean(name = "/messageService")
	// <2>
	HttpInvokerServiceExporter httpMessageService() {
		HttpInvokerServiceExporter http = new HttpInvokerServiceExporter();
		http.setServiceInterface(MessageService.class);
		http.setService(this.messageService());
		return http;
	}
}
