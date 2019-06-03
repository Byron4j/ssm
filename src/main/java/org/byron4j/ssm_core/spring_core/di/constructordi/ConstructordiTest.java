package org.byron4j.ssm_core.spring_core.di.constructordi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConstructordiTest {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("constructordi.xml");
		//applicationContext.getBean(ThingOne.class);
		
		System.out.println(applicationContext.getBean("constructordiWithType"));;
	}
}
