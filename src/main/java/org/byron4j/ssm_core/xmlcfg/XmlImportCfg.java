package org.byron4j.ssm_core.xmlcfg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * XML配置里面使用import引入其他的XML配置文件
 * @author BYRON.Y.Y
 *
 */
public class XmlImportCfg {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("import.xml");
		System.out.println(context.getBean("petStore"));

	}

}
