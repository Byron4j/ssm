

## 注册bean源码动作

以代码示例追踪：

```java
		GenericApplicationContext context = new GenericApplicationContext();
		new XmlBeanDefinitionReader(context).loadBeanDefinitions("services.xml", "daos.xml");
		context.refresh();
		System.out.println(context.getBean("petStore"));
```

### 类DefaultListableBeanFactory：

属性**beanDefinitionMap**是一个ConcurrentHashMap，保存了bean的信息。

```
org.springframework.beans.factory.support.DefaultListableBeanFactory

```

### 方法

委托给类

```
org.springframework.beans.factory.xml.BeanDefinitionParserDelegate
```

实际上调用：

```
org.springframework.beans.factory.support.BeanDefinitionReaderUtils.registerBeanDefinition(BeanDefinitionHolder, BeanDefinitionRegistry)
```

通过：

```
org.springframework.beans.factory.support.DefaultListableBeanFactory.registerBeanDefinition(String, BeanDefinition)
```

该方法代码：

- 如果不存在，且此次是第一次注册该bean，执行：

```java
this.beanDefinitionMap.put(beanName, beanDefinition);
this.beanDefinitionNames.add(beanName);
this.manualSingletonNames.remove(beanName);
```

- 如果不存在，已经在创建中了，执行：

```java
synchronized (this.beanDefinitionMap) {
	this.beanDefinitionMap.put(beanName, beanDefinition);
	List<String> updatedDefinitions = new ArrayList<>(this.beanDefinitionNames.size() + 1);
	updatedDefinitions.addAll(this.beanDefinitionNames);
	updatedDefinitions.add(beanName);
	this.beanDefinitionNames = updatedDefinitions;
	if (this.manualSingletonNames.contains(beanName)) {
		Set<String> updatedSingletons = new LinkedHashSet<>(this.manualSingletonNames);
		updatedSingletons.remove(beanName);
		this.manualSingletonNames = updatedSingletons;
	}
}
```

- 如果已经存在了，判断 ```isAllowBeanDefinitionOverriding()```，允许则覆盖，否则抛出异常



## refresh刷新工厂

```org.springframework.context.support.AbstractApplicationContext.refresh()```

```java
@Override
public void refresh() throws BeansException, IllegalStateException {
	synchronized (this.startupShutdownMonitor) {
		// Prepare this context for refreshing.
		prepareRefresh();

		// Tell the subclass to refresh the internal bean factory.
		ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

		// Prepare the bean factory for use in this context.
		prepareBeanFactory(beanFactory);

		try {
			// Allows post-processing of the bean factory in context subclasses.
			postProcessBeanFactory(beanFactory);

			// Invoke factory processors registered as beans in the context.
			invokeBeanFactoryPostProcessors(beanFactory);

			// Register bean processors that intercept bean creation.
			registerBeanPostProcessors(beanFactory);

			// Initialize message source for this context.
			initMessageSource();

			// Initialize event multicaster for this context.
			initApplicationEventMulticaster();

			// Initialize other special beans in specific context subclasses.
			onRefresh();

			// Check for listener beans and register them.
			registerListeners();

			// Instantiate all remaining (non-lazy-init) singletons.
			finishBeanFactoryInitialization(beanFactory);

			// Last step: publish corresponding event.
			finishRefresh();
		}

		catch (BeansException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("Exception encountered during context initialization - " +
						"cancelling refresh attempt: " + ex);
			}

			// Destroy already created singletons to avoid dangling resources.
			destroyBeans();

			// Reset 'active' flag.
			cancelRefresh(ex);

			// Propagate exception to caller.
			throw ex;
		}

		finally {
			// Reset common introspection caches in Spring's core, since we
			// might not ever need metadata for singleton beans anymore...
			resetCommonCaches();
		}
	}
}
```

### 1. prepareRefresh()

准备此上下文用于刷新、设置其启动日期和活动标志以及执行任何属性源的初始化。

```java
this.startupDate = System.currentTimeMillis();
this.closed.set(false);
this.active.set(true); // getBean的时候会检查active是否为true，否则抛出还未刷新的异常

initPropertySources(); // 初始化属性资源

// Validate that all properties marked as required are resolvable
// see ConfigurablePropertyResolver#setRequiredProperties
getEnvironment().validateRequiredProperties();  // 创建环境Environment

// Allow for the collection of early ApplicationEvents,
// to be published once the multicaster is available...
this.earlyApplicationEvents = new LinkedHashSet<>(); // 创建早期的应用事件
```

### 2. ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

创建bean工厂。

得到的是一个```DefaultListableBeanFactory```对象。

### 3. prepareBeanFactory(beanFactory);

准备bean工厂。

- 告诉内部bean工厂使用上下文的类加载器、bean定义的表达式解析器等等。


- 使用上下文回调配置bean工厂。


- 将bean的后置处理器注册为application监听器，用于检测内部bean。

```java
	beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));
```

- 检测LoadTimeWeaver并准备编织(如果找到的话)。

```java
	if (beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
		// loadTimeWeaver
		beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
		// Set a temporary ClassLoader for type matching.
		beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
	}
```

- 注册默认的环境bean。


```java
	if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME)) {
		// environment
		beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());
	}
	if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)) {
		// systemProperties
		beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());
	}
	if (!beanFactory.containsLocalBean(SYSTEM_ENVIRONMENT_BEAN_NAME)) {
		// systemEnvironment
		beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());
	}
```


## 获取getBean

### 检查工厂状态

```java
org.springframework.context.support.AbstractApplicationContext.assertBeanFactoryActive()

protected void assertBeanFactoryActive() {
	if (!this.active.get()) {
		if (this.closed.get()) {
			throw new IllegalStateException(getDisplayName() + " has been closed already");
		}
		else {
			throw new IllegalStateException(getDisplayName() + " has not been refreshed yet");
		}
	}
}
```

### getBean

DefaultListableBeanFactory.getBean(String beanName) --> 逻辑是由抽象工厂实现的：

```java
org.springframework.beans.factory.support.AbstractBeanFactory.getBean(String)
	+--org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(String, Class<T>, Object[], boolean)
	
```

- beanName转换： 将**&**截掉。

```java
final String beanName = transformedBeanName(name);
```

- 检查单例缓存中是否存在

**singletonObjects** : 单例对象的缓存：beanName --》 instance
**earlySingletonObjects**: 早期单例对象的缓存： beanName --》 instance
**singletonFactories**: 单例工厂的缓存：bean name --》ObjectFactory 

>先后先单例缓存**singletonObjects**中查找，找到即返回;
>
>单例缓存中不存在且正在并发地创建中的话，则从早期单例缓存中**earlySingletonObjects**查找，找到即返回;
>
>早期单例缓存中不存在且允许早期引用的话，则从单例工厂获取;单例工厂中获取到的话，将其塞入**早期单例缓存earlySingletonObjects**和**单例对象的缓存singletonFactories**中。
>
>【注意】：
>
>这三个map将用来处理单例bean地循环依赖

```java
org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(String, boolean)

@Nullable
protected Object getSingleton(String beanName, boolean allowEarlyReference) {
	Object singletonObject = this.singletonObjects.get(beanName);
	if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
		synchronized (this.singletonObjects) {
			singletonObject = this.earlySingletonObjects.get(beanName);
			if (singletonObject == null && allowEarlyReference) {
				ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
				if (singletonFactory != null) {
					singletonObject = singletonFactory.getObject();
					this.earlySingletonObjects.put(beanName, singletonObject);
					this.singletonFactories.remove(beanName);
				}
			}
		}
	}
	return singletonObject;
}
```


### 实例化bean

- 如果getBean找到了bean, 则获取给定bean实例的对象，对于FactoryBean，要么是bean实例本身，要么是它创建的对象。调用```AbstractAutowireCapableBeanFactory.getObjectForBeanInstance```方法，处理其依赖地其它bean。

```java
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.getObjectForBeanInstance(Object, String, String, RootBeanDefinition)
```

- getbean没有找到的话，则需要创建

```java
org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(String)


+-- org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(String, RootBeanDefinition, Object[])
	+--org.springframework.beans.factory.support.AbstractBeanFactory.resolveBeanClass(RootBeanDefinition, String, Class<?>...)
		+--org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(String, RootBeanDefinition, Object[]) 实例化
			+--org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(String, RootBeanDefinition, Object[])
				+--org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyPropertyValues(String, BeanDefinition, BeanWrapper, PropertyValues) 处理依赖的属性bean
```
