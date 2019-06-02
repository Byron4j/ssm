package org.byron4j.ssm_core.di.constructordi;

public class ThingOne {
	public ThingOne(ThingTwo thingTwo, ThingThree thingThree) {
        // ...
		System.out.println("调用:org.byron4j.ssm_core.constructordi.ThingOne.ThingOne(ThingTwo, ThingThree)");
    }
}
