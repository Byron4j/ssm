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


## 基于setter注入

基于setter的DI是通过容器调用bean上的setter方法来实现的，这些方法是在调用无参数构造函数或无参数静态工厂方法来实例化bean之后调用的。

### xml配置

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">

	<!-- setter注入 -->
	
    <bean id = "setterDiEmployee"  class = "org.byron4j.ssm_core.setterdi.SetterDiEmployee">
    	<property name="address" ref = "setterDiAddress"></property>
    	
    	<property name="dept">
    		<ref bean="setterDiDept"/>
    	</property>
    	
    	<property name="age" value="18"></property>
    </bean>
    
    <bean id = "setterDiAddress" class = "org.byron4j.ssm_core.setterdi.SetterDiAddress"></bean>
    
    <bean id = "setterDiDept" class = "org.byron4j.ssm_core.setterdi.SetterDiDept"></bean>
	

</beans>
```

### java示例

```java
package org.byron4j.ssm_core.setterdi;

import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class SetterDiEmployee {
	private SetterDiAddress address;
	private SetterDiDept dept;
	private String name;
	private int age;
}

```



## 选择：构造器注入还是setter注入？

Spring官方建议：

>由于可以混合使用基于构造函数和基于setter的DI，因此对于强制依赖项使用构造函数，对于可选依赖项使用setter方法或配置方法，这是一个很好的经验法则。
>
>Spring团队通常提倡构造函数注入，因为它允许您将应用程序组件实现为不可变的对象，并确保所需的依赖项不是空的。此外，注入构造函数的组件总是以完全初始化的状态返回给客户机(调用)代码。
>
>Setter注入主要应该只用于可选的依赖项，这些依赖项可以在类中分配合理的默认值。setter注入的一个好处是，setter方法使该类的对象能够稍后重新配置或重新注入。因此，通过[JMX mbean](https://docs.spring.io/spring/docs/5.1.6.RELEASE/spring-framework-reference/integration.html#jmx) 进行管理是setter注入的一个引人注目的用例。
>
>使用对特定类最有意义的DI样式。有时候，当处理您没有源的第三方类时，就会为您做出选择。例如，如果第三方类没有公开任何setter方法，那么构造函数注入可能是惟一可用的DI形式。


## 依赖和配置的的细节

像之前示例的一样，你可以定义bean属性和构造器参数定义为对其他bean的引用或者定义为内联的值。Spring基于XML的配置提供了 ``<property/>`` 和 ``<constructor-arg/>`` 达到此目的。

### 直接值（基本类型、String等其它）

``<property/>`` 元素的 ``value`` 属性




