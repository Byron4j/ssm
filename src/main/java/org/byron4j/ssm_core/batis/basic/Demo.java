package org.byron4j.ssm_core.batis.basic;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.byron4j.ssm_core.batis.basic.mapper.UserMapper;
import org.byron4j.ssm_core.batis.basic.module.User;

public class Demo {
	public static void main(String[] args) throws Exception {
		String resource = "batis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			User user = mapper.selectUser(2);
			System.out.println("user=" + user);
		} finally {
		  session.close();
		}
	}
}
