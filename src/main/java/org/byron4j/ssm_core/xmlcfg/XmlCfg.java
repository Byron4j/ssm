package org.byron4j.ssm_core.xmlcfg;

import org.byron4j.ssm_core.xmlcfg.service.PetStoreServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 基于XML配置的IOC容器
 * @author BYRON.Y.Y
 *
 */
public class XmlCfg {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
		System.out.println(context.getBean(PetStoreServiceImpl.class));
		System.out.println(context.getBean("petStore"));
		
	}
}
