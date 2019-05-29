package org.byron4j.ssm_core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Hello world!
 *
 */
@Configuration
@PropertySource("classpath:App.properties")
public class App {

    @Autowired
    Environment env;

    @Bean
    public TestBean testBean() {
        TestBean testBean = new TestBean();
        testBean.setName(env.getProperty("testbean.name"));
        return testBean;
    }
    
    @Getter
    @Setter
    @ToString
    class TestBean{
    		private String name;
    }
    
    public static void main(String[] args) {
		AnnotationConfigApplicationContext annotationConfigApplicationContext
		 	= new AnnotationConfigApplicationContext(App.class);
		System.out.println(annotationConfigApplicationContext.getBean(App.class).testBean());
	}
}