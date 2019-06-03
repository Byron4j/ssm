package org.byron4j.ssm_core.setterdi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterDiTest {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("setterdi.xml");
		
		System.out.println(applicationContext.getBean("setterDiEmployee"));
	}
}
