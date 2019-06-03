package org.byron4j.ssm_core.spring_core.di.didetails;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.Setter;

@Setter
public class PropertiesDi {
	
	private Properties properties;
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("didetails/didetails.xml");
		applicationContext.getBean(PropertiesDi.class).properties.entrySet().forEach(ele -> {System.out.println(ele);});
		
	}
}
