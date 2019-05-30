package org.byron4j.ssm_core.factory;

import org.byron4j.ssm_core.xmlcfg.dao.AccountDao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.ToString;

@ToString
public class DefaultListableBeanFactoryDemo {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("import.xml");
		BeanFactory beanFactory = context.getBeanFactory();
		if(beanFactory instanceof DefaultListableBeanFactory) {
			System.out.println("手动注册bean");
			
			((DefaultListableBeanFactory) beanFactory)
				.registerSingleton("demo", new DefaultListableBeanFactoryDemo());
		}
		
		System.out.println(context.getBean("demo"));
		
	}
	
	private AccountDao accountDao;
}
