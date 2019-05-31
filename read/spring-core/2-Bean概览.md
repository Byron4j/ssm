# 实例化bean

## 构造器实例化

## 静态工厂实例化

**factory-method**：属性指定静态工厂方法


- xml配置

```xml
<bean id="clientService"
    class="examples.ClientService"
    factory-method="createInstance"/>
```


- java示例

```java
public class ClientService {
    private static ClientService clientService = new ClientService();
    private ClientService() {}

    public static ClientService createInstance() {
        return clientService;
    }
}
```

## 使用实例工厂方法实例化(非静态)


要使用此机制，**class属性为空**，并在**factory-bean**属性中指定(或父或父)容器中用来创建该实例的基础bean的名称，**factory-method**属性指定基础bean中用来创建实例的方法。

>以下实例： 定义了类型为 examples.DefaultServiceLocator 的bean名为serviceLocator；
>
>定义了名为clientService的bean，以serviceLocator为基础bean，通过调用serviceLocator的createClientServiceInstance方法来实例化clientService；
>
>定义了名为accountService的bean，以serviceLocator为基础bean，通过调用serviceLocator的createClientServiceInstance方法来实例化accountService。

- xml配置

```xml
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
    <!-- inject any dependencies required by this locator bean -->
</bean>

<bean id="clientService"
    factory-bean="serviceLocator"
    factory-method="createClientServiceInstance"/>

<bean id="accountService"
    factory-bean="serviceLocator"
    factory-method="createAccountServiceInstance"/>
```

- java示例

```java
public class DefaultServiceLocator {

    private static ClientService clientService = new ClientServiceImpl();

    private static AccountService accountService = new AccountServiceImpl();

    public ClientService createClientServiceInstance() {
        return clientService;
    }

    public AccountService createAccountServiceInstance() {
        return accountService;
    }
}
```


