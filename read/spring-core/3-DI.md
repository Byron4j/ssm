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

### 直接值（基本类型、String等其它）注入

#### 基本类型注入

``<property/>`` 元素的 ``value`` 属性指定了一个属性或者构造器的参数。Spring的[转换服务](https://docs.spring.io/spring/docs/5.1.7.RELEASE/spring-framework-reference/core.html#core-convert-ConversionService-API) 会将这些属性的value从String转换为属性或者构造器参数的真实类型。

以下是一个实例, 定义了一个类型为org.apache.commons.dbcp.BasicDataSource的myDataSource的bean，并且使用 ``property`` 元素的 ``value`` 属性将其相关属性字段进行了赋值。

```xml
<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <!-- results in a setDriverClassName(String) call -->
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
    <property name="username" value="root"/>
    <property name="password" value="masterkaoli"/>
</bean>
```

等同于使用[p命名空间](https://docs.spring.io/spring/docs/5.1.7.RELEASE/spring-framework-reference/core.html#beans-p-namespace)的更简洁的方式：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource"
        destroy-method="close"
        p:driverClassName="com.mysql.jdbc.Driver"
        p:url="jdbc:mysql://localhost:3306/mydb"
        p:username="root"
        p:password="masterkaoli"/>

</beans>
```


#### java.util.Properties类型注入

Spring容器通过使用javabean PropertyEditor机制实现将``<value/>``元素中的文本转换为java.util.Properties。关联类：PropertyEditorRegistrySupport

- xml配置

```xml
<bean id="propertiesDi" class = "org.byron4j.ssm_core.di.didetails.PropertiesDi">
	<property name="properties">
        <value>
            jdbc.driver.className=com.mysql.jdbc.Driver
            jdbc.url=jdbc:mysql://localhost:3306/mydb
        </value>
    </property>
</bean>
```

- java

```java
package org.byron4j.ssm_core.di.didetails;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.Setter;

@Setter
public class PropertiesDi {
	
	private Properties properties;
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("didetails/didetails.xml");
		applicationContext.getBean(PropertiesDi.class).properties.entrySet().forEach(ele -> {System.out.println(ele);});
		
	}
}

```


### idref 元素

idref元素传递bean的id作为另一个bean的``<constructor-arg/> ``或者``<property/>``的引用。

```xml
<bean id="theTargetBean" class="..."/>

<bean id="theClientBean" class="...">
    <property name="targetName">
        <idref bean="theTargetBean"/>
    </property>
</bean>
```

等同于以下配置：

```xml
<bean id="theTargetBean" class="..." />

<bean id="client" class="...">
    <property name="targetName" value="theTargetBean"/>
</bean>
```

第一种表单比第二种更可取，因为使用```idref```标记可以让容器在部署时验证引用的命名bean是否确实存在。
在第二个变体中，对传递给客户机bean的targetName属性的值不执行验证, 只有在实际实例化bean时才会发现拼写错误(很可能导致致命的结果)。

>```idref```的```local```属性在Spring4.0 beans XSD已经被废弃，请使用```idref```的```bean```属性代替。

在使用ProxyFactoryBean配置AOP的时候，使用```<idref/>```可以防止拼写错误。


### 对其他bean的引用(协作者)

```ref``` 是```<constructor-arg/>``` or ```<property/>```中的最后的元素。在这里，你可以设置指定的属性的value为另一个被容器管理的bean。
该属性所引用的依赖bean需要在该属性值被设置之前被初始化（如果合作者是一个单例bean，那么它可能已经被容器初始化了）。所有的引用基本上都是引用另一个对象。

范围和验证依赖于你通过```bean```、```local```或者```parent```属性指定的ID或者name。

通过```idref```标签的```bean```属性指定目标bean是常用的形式，并且允许创建对同一容器或者父容器中任意bean的引用。
```bean```属性和```id```属性和```name```属性大体一致。以下是一个使用```ref```元素的的示例：

```xml
<ref bean="someBean"/>
```

通过```parent```属性可以创建一个当前容器的父容器中的bean的引用。parent属性和目标bean的id、name属性含义类似。
目标bean必须是当前容器的一个父容器。
你应该在拥有层次结构的容器时，且想通过一个代理包装相同的name的父容器中的bean的时候使用parent引用bean。

父容器中的配置：
```xml
<!-- in the parent context -->
<bean id="accountService" class="com.something.SimpleAccountService">
    <!-- insert dependencies as required as here -->
</bean>
```
子容器中的配置：
```xml
<!-- in the child (descendant) context -->
<bean id="accountService" <!-- bean name is the same as the parent bean -->
    class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target">
        <ref parent="accountService"/> <!-- notice how we refer to the parent bean -->
    </property>
    <!-- insert other configuration and dependencies as required here -->
</bean>
```

### 内部bean注入

```<property/>``` or ```<constructor-arg/>``` 元素的里面的```<bean/>```元素定义了一个内部bean：

```xml
<bean id="outer" class="...">
    <!-- instead of using a reference to a target bean, simply define the target bean inline -->
    <property name="target">
        <bean class="com.example.Person"> <!-- this is the inner bean -->
            <property name="name" value="Fiona Apple"/>
            <property name="age" value="25"/>
        </bean>
    </property>
</bean>
```

内部bean的定义不需要已定义好的ID或者name。如果指定了，容器也不会使用它作为标识符。容器也会在创建时忽略```scope```标志，因为内部bean总是匿名的、总是随着外部bean创建一块创建的。不可能独立地访问内部bean，也不可能将它们注入到协作bean而不是封闭bean中。

作为一个特例，可以从自定义的范围接收销毁回调————例如，对于一个单例bean的```request```范围的内部bean。
内部bean的创建过程绑定到了它的外部类中，但是销毁回调允许它参与请求范围的生命周期。这并不是一个常用场景，内部bean通常不会共享它们的容器bean的范围。

### 集合注入

```<list/>```、```<set/>```、```<map/>```和```<props/>```元素分别设置Java集合类型List、Set、Map和Properties类型，如下示例：

```xml
<bean id="moreComplexObject" class="example.ComplexObject">
    <!-- results in a setAdminEmails(java.util.Properties) call -->
    <property name="adminEmails">
        <props>
            <prop key="administrator">administrator@example.org</prop>
            <prop key="support">support@example.org</prop>
            <prop key="development">development@example.org</prop>
        </props>
    </property>
    <!-- results in a setSomeList(java.util.List) call -->
    <property name="someList">
        <list>
            <value>a list element followed by a reference</value>
            <ref bean="myDataSource" />
        </list>
    </property>
    <!-- results in a setSomeMap(java.util.Map) call -->
    <property name="someMap">
        <map>
            <entry key="an entry" value="just some string"/>
            <entry key ="a ref" value-ref="myDataSource"/>
        </map>
    </property>
    <!-- results in a setSomeSet(java.util.Set) call -->
    <property name="someSet">
        <set>
            <value>just some string</value>
            <ref bean="myDataSource" />
        </set>
    </property>
</bean>
```

#### java类定义

```java
@Setter
@ToString
public class CollectionsDI {

    private Properties adminEmails;
    List someList;
    Map someMap;
    Set someSet;

    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("didetails/Collections.xml");
        System.out.println(applicationContext.getBean(CollectionsDI.class));
    }
}
```

#### xml配置

```xml
<import resource="classpath*:services.xml"/>
    <bean id="moreComplexObject" class="org.byron4j.ssm_core.spring_core.di.didetails.CollectionsDI">
        <!-- results in a setAdminEmails(java.util.Properties) call -->
        <property name="adminEmails">
            <props>
                <prop key="administrator">administrator@example.org</prop>
                <prop key="support">support@example.org</prop>
                <prop key="development">development@example.org</prop>
            </props>
        </property>
        <!-- results in a setSomeList(java.util.List) call -->
        <property name="someList">
            <list>
                <value>a list element followed by a reference</value>
                <ref bean="accountDao" />
            </list>
        </property>
        <!-- results in a setSomeMap(java.util.Map) call -->
        <property name="someMap">
            <map>
                <entry key="an entry" value="just some string"/>
                <entry key ="a ref" value-ref="accountDao"/>
            </map>
        </property>
        <!-- results in a setSomeSet(java.util.Set) call -->
        <property name="someSet">
            <set>
                <value>just some string</value>
                <ref bean="accountDao" />
            </set>
        </property>
    </bean>

```

#### 运行结果

```
CollectionsDI(adminEmails={support=support@example.org, administrator=administrator@example.org, development=development@example.org}, someList=[a list element followed by a reference, org.byron4j.ssm_core.spring_core.xmlcfg.dao.AccountDao@13c10b87], someMap={an entry=just some string, a ref=org.byron4j.ssm_core.spring_core.xmlcfg.dao.AccountDao@13c10b87}, someSet=[just some string, org.byron4j.ssm_core.spring_core.xmlcfg.dao.AccountDao@13c10b87])

```


map、set 也可以是以下任意类型的值：

```xml
bean | ref | idref | list | set | map | props | value | null
```

### 集合合并

Spring容器还支持合并集合。应用开发者可以定义父集合```<list/>, <map/>, <set/> or <props/>```，并且定义子集合```<list/>, <map/>, <set/> or <props/>```继承父集合中的值。
也就是说，子集合的值是父集合、子集合的合并的结果，子集合元素覆盖父集合中指定的值。
关于父、子bean的关系可以参考[相关部分](https://docs.spring.io/spring/docs/5.1.7.RELEASE/spring-framework-reference/core.html#beans-child-bean-definitions)。

以下示例示范了集合的合并：

```xml
<beans>
    <bean id="parent" abstract="true" class="example.ComplexObject">
        <property name="adminEmails">
            <props>
                <prop key="administrator">administrator@example.com</prop>
                <prop key="support">support@example.com</prop>
            </props>
        </property>
    </bean>
    <bean id="child" parent="parent">
        <property name="adminEmails">
            <!-- the merge is specified on the child collection definition -->
            <props merge="true">
                <prop key="sales">sales@example.com</prop>
                <prop key="support">support@example.co.uk</prop>
            </props>
        </property>
    </bean>
<beans>
```


注意到 ```merge=true``` 设置，当child 被容器解析和初始化的时候，产生的实例Properties类型的属性adminEmails，即包含了child、parent二者的集合的合并。
合并后的结果如下：

```xml
administrator=administrator@example.com
sales=sales@example.com
support=support@example.co.uk
```

这种合并行为类似地应用于`````<list/>`````、`````<map/>```和```<set/>```集合类型。
例如List类型，将遵循其语义，父列表的值位于子列表的所有值之前。

>不能合并不同类型的集合，如Map和List，否则将抛出一个异常。
>
>```merge``` 属性应该在低层次中指定，如child层。放在parent层是多余的毫无意义的。

### 强类型集合

随着Java 5中泛型类型的引入，你可以使用强类型的集合。如果使用Spring来依赖—将强类型集合注入bean，你可以利用Spring的类型转换支持，以便在将强类型集合实例的元素添加到集合之前将其转换为适当的类型。

```java
public class SomeClass {

    private Map<String, Float> accounts;

    public void setAccounts(Map<String, Float> accounts) {
        this.accounts = accounts;
    }
}
```


```xml
<beans>
    <bean id="something" class="x.y.SomeClass">
        <property name="accounts">
            <map>
                <entry key="one" value="9.99"/>
                <entry key="two" value="2.75"/>
                <entry key="six" value="3.99"/>
            </map>
        </property>
    </bean>
</beans>
```

当```something```这个bean的```accounts```属性要被注入的时候，通过反射提供关于强类型映射<String, Float>的元素类型的泛型信息。

因此Spring类型转换机制可以将各种值元素识别为Float类型，如（9.99, 2.75, and 3.99）并且将她们转换为真正的Float类型。

### Null和空字符串值注入

**Spring将属性的空参数作为空字符串来处理**。以下实例将属性email 设置为了空的字符串""：

```xml
<bean class="ExampleBean">
    <property name="email" value=""/>
</bean>
```

等同于java代码： ```exampleBean.setEmail("");```

```<null/>``` 标签用来处理null值：

```xml
<bean class="ExampleBean">
    <property name="email">
        <null/>
    </property>
</bean>
```

等同于java代码：```exampleBean.setEmail(null);```

### 使用p命名空间的XML快捷配置方式

```p```命名空间用来代替属性标签```<property/>```。
Spring支持带有名称空间的可扩展配置格式，基于XML模式定义。但是，p-namespace没有在XSD文件中定义，只存在于Spring的核心中。

你首先需要引入XML的p命名空间：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean name="classic" class="com.example.ExampleBean">
        <property name="email" value="someone@somewhere.com"/>
    </bean>

    <bean name="p-namespace" class="com.example.ExampleBean"
        p:email="someone@somewhere.com"/>
</beans>
```


```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean name="john-classic" class="com.example.Person">
        <property name="name" value="John Doe"/>
        <property name="spouse" ref="jane"/>
    </bean>

    <bean name="john-modern"
        class="com.example.Person"
        p:name="John Doe"
        p:spouse-ref="jane"/>

    <bean name="jane" class="com.example.Person">
        <property name="name" value="Jane Doe"/>
    </bean>
</beans>
```

第一个bean定义使用了```<property name="spouse" ref="jane"/>``` ，属性spouse引用了另一个叫jane的bean。
第二个定义使用了 ```p:spouse-ref="jane"``` ，p:attrName-ref 定义属性的依赖bean。
二者是等同的效果。
```-ref``` 表明了该属性不是一个直接的值，而是一个对其它bean的引用。

>p-namespace不如标准XML格式灵活。

### 使用c命名空间的XML快捷配置方式

Spring 3.1开始，```c```命名空间等效于基于构造器注入的配置：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:c="http://www.springframework.org/schema/c"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="beanTwo" class="x.y.ThingTwo"/>
    <bean id="beanThree" class="x.y.ThingThree"/>

    <!-- traditional declaration with optional argument names -->
    <bean id="beanOne" class="x.y.ThingOne">
        <constructor-arg name="thingTwo" ref="beanTwo"/>
        <constructor-arg name="thingThree" ref="beanThree"/>
        <constructor-arg name="email" value="something@somewhere.com"/>
    </bean>

    <!-- c-namespace declaration with argument names -->
    <bean id="beanOne" class="x.y.ThingOne" c:thingTwo-ref="beanTwo"
        c:thingThree-ref="beanThree" c:email="something@somewhere.com"/>

</beans>
```

对于构造函数参数名不可用的少数情况(通常如果字节码是在没有调试信息的情况下编译的)，可以使用回退参数索引，如下所示:

```xml
<!-- c-namespace index declaration -->
<bean id="beanOne" class="x.y.ThingOne" c:_0-ref="beanTwo" c:_1-ref="beanThree"
    c:_2="something@somewhere.com"/>
```

### 复合属性名

在设置bean属性时，可以使用复合或嵌套属性名，只要名称路径上的属性名不为null。

```xml
<bean id="something" class="things.ThingOne">
    <property name="fred.bob.sammy" value="123" />
</bean>
```

something这个bean的属性fred，fred的属性bob，bob的属性sammy被设置为123。
为了使其工作，在构造bean之后，something的fred属性和fred的bob属性必须不为空。否则会抛出NullPointerException。


## 使用 ```depends-on```

如果一个bean是另一个bean的依赖项, 意味着这个bean会被设置为另一个bean的属性。典型的应用是你可以在基于XML配置中使用```<ref/>```。
然而，有时候bean之间的依赖关系不是那么直接。例如，需要触发类中的静态初始化器，例如数据库驱动程序注册。

```depends-on``` 属性可以显式强制在使用此元素的bean初始化之前初始化一个或多个bean。

```xml
<bean id="beanOne" class="ExampleBean" depends-on="manager"/>
<bean id="manager" class="ManagerBean" />
```

要表示对多个bean的依赖，可以提供一个bean名称列表作为依赖属性的值(逗号、空格和分号是有效的分隔符):

```xml
<bean id="beanOne" class="ExampleBean" depends-on="manager,accountDao">
    <property name="manager" ref="manager" />
</bean>

<bean id="manager" class="ManagerBean" />
<bean id="accountDao" class="x.y.jdbc.JdbcAccountDao" />
```


>```depends-on``` 属性既可以指定初始化期间的依赖项，也可以指定相应的销毁期间的依赖项。在销毁定义了depends-on属性的bean之前，先销毁其依赖的bean，因此可以控制shutdown的顺序。

## 延迟加载的bean

默认情况下，```ApplicationContext``` 实现会在初始化过程中急切地创建和配置所有单例bean。
一般而言，这种预实例化是可取的，因为配置或周围环境中的错误会立即被发现。当这个行为不需要的话，你可以通过标记bean地定义为懒加载来阻止预初始化。一个懒加载地bean告诉IOC容器在它第一次被使用的时候才实例化bean而不是启动的时候去实例化它。

在XML中，这个行为控制通过```<bean/>```元素的```lazy-init```属性控制：

```xml
<bean id="lazy" class="com.something.ExpensiveToCreateBean" lazy-init="true"/>
<bean name="not.lazy" class="com.something.AnotherBean"/>
```

>但是，如果一个标记为懒加载的bean被一个单例bean引用的时候，他还是会在启动时被加载，因为需要作为单例bean的依赖项。


你还可以控制容器的默认懒加载级别，通过```<beans/>```的```default-lazy-init```属性：

```xml
<beans default-lazy-init="true">
    <!-- no beans will be pre-instantiated... -->
</beans>
```


## 自动装配autowire属性

在Spring容器中是可以在协作的beans间进行自动装配的。通过检查ApplicationContext的内容，可以让Spring为你的bean自动的解析协作者（其它bean）。
自动装配的优点：

- 自动装配可以减少指定属性和构造器参数的个数。
- 自动装配可以随着对象的发展变更配置。例如，如果你需要添加一个依赖到一个类中，依赖可以自动匹配而不需要你去修改配置。因此，自动装配在开发过程中是非常有用的。

当你使用基于XML配置的元数据时，你可以在```<bean/>```元素的属性```autowire```中指定自动装配模式。
自动装配有4种模式：

- **no** 
    - 默认的，不会自动装配。bean引用必须由```ref```元素定义。对于大型的部署应用不太推荐改变默认的设置，因为显示地指定协作者可以提供更好更清晰地控制。在一定程度上，它记录了系统的结构。
    
- **byName**
    - 根据属性名自动装配。Spring按属性名查找需要被自动装配的bean。例如，如果一个bean被设置为按name自动装配，且它包含一个```master```属性（当然，也有```setMaster(..)```方法），Spring会查找一个命名为```master```的bean并将其设置为属性值。
    
- **byType**
    - 如果属性类型的bean存在于容器中，则按类型自动装配。如果多于一个，则会抛出严重的异常，表示你不应该使用byType的自动装配。如果没有符合的bean，则不会做任何事情（属性不会set值）。
    
- **constructor**
    - 类似于byType但是适用于构造器参数。但是如果没有符合的bean，则会抛出严重的异常。
    
    
对于```byType```或者```constructor```模式，你可以连接数组和类型化集合。在这种情况下，容器中的所有符合类型的自动装配的候选者会被提供以满足依赖项。
你可以自动装配强类型的Map实例如果期望的类型是String的话。一个自动装配的Map实例的值由所有符合期望的bean实例组成，Map实例的key包含了响应的bean name。

### 自动装配的局限性和缺陷

自动装配在使用一致的项目中是可以很好工作的。如果不经常使用自动装配，那么开发人员可能对于它连接一个或者两个bean的定义感到困惑。
考虑自动装配的缺陷和限制：

- 在```property```和```constructor-arg```中显示设置的依赖会覆盖自动装配。
    - 你不能自动装配原始类型、String和Classes（和这些类型的数组），这是设计的限制。
    
- 自动装配不如显示连接更精准。虽然，Spring小心的避免猜测，以免导致不期望的结果。Spring管理对象的关系不再明确的记录下来了。

- 连接信息可能对于生成文档信息的工具不可用。

- 容器中的多个bean定义可以匹配要自动装配的setter方法或构造函数参数指定的类型。对于数组、集合或者Map实例，这并不是一个必须的问题。但是，对于期望依赖的是一个单个值，这种模糊性不是能任意解决的。如果非唯一的bean定义可用的话，会抛出一个异常。

在后面的场景中，你有几个选择：

- 在需要显示连接的地方放弃自动装配

- 通过设置```autowire-candidate```属性为```false```可以避免自动装配。

- 指定一个bean作为主要的primary的候选，在```<bean/>```元素的```primary```属性设置为```true```达到此目的。

- 通过基于注解的配置实现更细粒度的控制，在[基于注解的容器配置](https://docs.spring.io/spring/docs/5.1.7.RELEASE/spring-framework-reference/core.html#beans-annotation-config)可以了解。

### 从自动装配中排除Bean

在XML格式中，通过将```<bean/>```的```autowire-candidate```属性设置为```false```可以排除自动装配。容器会标记指定的bean在自动装配机制中不可用。（包括注解风格的配置[@Autowired](https://docs.spring.io/spring/docs/5.1.7.RELEASE/spring-framework-reference/core.html#beans-autowired-annotation)）

>```autowire-candidate```属性只影响基于类型的自动装配。不会对指定通过name引用的造成影响，因此，如果名称匹配，按名称自动装配仍然会注入bean。

你可以通过bean名称的模式匹配来限制自动装配候选。顶级的```<beans/>```元素接收一个或者多个模式，在```default-autowire-candidate```属性中配置。
例如，限制name以Repository结尾的bean的自动装配的候选状态，可以使用 Repository。为了定义多个模式，在一个逗号分隔的列表中定义。