package org.byron4j.ssm_core.instantiationform;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BookStore {
	private static Book book = new Book();
	
	public Book acquireBook() {
		System.out.println("调用：org.byron4j.ssm_core.instantiationform.BookStore.acquireBook()");
		return book;
	}
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("instancefactory.xml");
		applicationContext.getBean(Book.class);
		
	}
}
