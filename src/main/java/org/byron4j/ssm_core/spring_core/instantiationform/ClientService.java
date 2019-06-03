package org.byron4j.ssm_core.spring_core.instantiationform;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientService {
	
	private static ClientService instance = new ClientService();
	
	public static ClientService staticInstation() {
		System.out.println("调用：org.byron4j.ssm_core.instantiationform.ClientService.staticInstation()");
		return instance;
	}
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("staticfactoryinstiation.xml");
		applicationContext.getBean(ClientService.class);
	}
}
