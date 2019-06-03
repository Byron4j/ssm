package org.byron4j.ssm_core.spring_core.setterdi;

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
