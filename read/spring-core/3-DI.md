# DI依赖注入

依赖项注入(Dependency injection, DI)是一个过程，在这个过程中，对象仅通过构造函数参数、到工厂方法的参数或从工厂方法构造或返回对象实例后在对象实例上设置的属性来定义它们的依赖项(即与它们一起工作的其他对象)。

DI存在两种主要变体:基于构造函数的依赖项注入和基于sett的依赖项注入。


## 构造器注入

基于构造函数的DI是由容器调用一个构造函数来完成的，该构造函数有许多参数，每个参数表示一个依赖项。

### xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">


	
    <bean id="thingTwo" class="org.byron4j.ssm_core.constructordi.ThingTwo" />
    <bean id="thinghree" class="org.byron4j.ssm_core.constructordi.ThingThree" />


	<bean id="thingone" class="org.byron4j.ssm_core.constructordi.ThingOne">
		<constructor-arg ref="thingTwo"></constructor-arg>
		<constructor-arg ref="thinghree"></constructor-arg>
	</bean>
	
	
	<bean id="constructordiWithType" class="org.byron4j.ssm_core.constructordi.ConstructordiWithType">
		<constructor-arg type="String" value="Byron"/>
		<constructor-arg name="age" value="20"/>
		<constructor-arg ref="thingone"/>
	</bean>
	
	

</beans>
```

### java示例

```java
package org.byron4j.ssm_core.constructordi;

public class ThingOne {
	public ThingOne(ThingTwo thingTwo, ThingThree thingThree) {
        // ...
		System.out.println("调用:org.byron4j.ssm_core.constructordi.ThingOne.ThingOne(ThingTwo, ThingThree)");
    }
}

```

```java
package org.byron4j.ssm_core.constructordi;

import lombok.ToString;

@ToString
public class ConstructordiWithType {
	private String name;
	private int age;
	private ThingOne thingOne;
	
	public ConstructordiWithType(String name, int age, ThingOne thingOne) {
		this.name = name;
		this.age = age;
		this.thingOne = thingOne;
		System.out.println("调用：org.byron4j.ssm_core.constructordi.WithFieldIsPrimaryType.ConstructordiWithType(String, int, ThingOne)");
	}
}

```

