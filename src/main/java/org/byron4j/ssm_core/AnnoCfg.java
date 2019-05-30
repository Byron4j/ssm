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
 * 基于JAVA注解的IOC容器
 */
@Configuration
@PropertySource("classpath:App.properties")
public class AnnoCfg {

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
		 	= new AnnotationConfigApplicationContext(AnnoCfg.class);
		System.out.println(annotationConfigApplicationContext.getBean(AnnoCfg.class).testBean());
	}
}