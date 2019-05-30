package org.byron4j.ssm_core.xmlcfg;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

/**
 * 最灵活的变体是GenericApplicationContext与reader委托相结合——例如，使用XmlBeanDefinitionReader来处理XML文件
 * @author BYRON.Y.Y
 *
 */
public class GaXMLReaderCfg {
	public static void main(String[] args) {
		GenericApplicationContext context = new GenericApplicationContext();
		new XmlBeanDefinitionReader(context).loadBeanDefinitions("services.xml", "daos.xml");
		context.refresh();
		System.out.println(context.getBean("petStore"));
	}

}
