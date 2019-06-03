package org.byron4j.ssm_core.spring_core.ioc.customerInistiantLogic;

import org.byron4j.ssm_core.spring_core.instantiationform.Book;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 自定义初始化逻辑通过实现 FactoryBean
 * @author BYRON.Y.Y
 *
 */
public class BookFactoryBeanDemo implements FactoryBean<Book>{

	@Override
	public Book getObject() throws Exception {
		System.out.println("调用org.byron4j.ssm_core.spring_core.ioc.customerInistiantLogic.BookFactoryBeanDemo.getObject()");
		Book book = new Book();
		return book;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-ioc/customerInistiantLogic.xml");
		System.out.println(applicationContext.getBean("book"));  // 获取Book实例
		System.out.println(applicationContext.getBean("&book")); // 获取BookFactoryBeanDemo实例
	}

}
