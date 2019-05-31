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
