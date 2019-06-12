package org.byron4j.ssm_core.spring_core.di.didetails;

import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Setter
@ToString
public class CollectionsDI {

    private Properties adminEmails;
    List someList;
    Map someMap;
    Set someSet;

    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("didetails/Collections.xml");
        //System.out.println(applicationContext.getBean(CollectionsDI.class));
        System.out.println(applicationContext.getBean("moreComplexObject-p"));
    }
}
