package org.byron4j.ssm_core.spring_core.properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReadPropertiesDemo {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("didetails/properties.xml");
		BasicDataSource basicDataSource = (BasicDataSource)applicationContext.getBean("dataSource");
		System.out.println(basicDataSource.getDriverClassName());
		System.out.println(basicDataSource.getUrl());
		System.out.println(basicDataSource.getUsername());
		System.out.println(basicDataSource.getPassword());
	}
}
