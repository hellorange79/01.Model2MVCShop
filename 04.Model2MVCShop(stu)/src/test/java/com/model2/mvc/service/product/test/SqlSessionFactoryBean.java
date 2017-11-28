package com.model2.mvc.service.product.test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactoryBean {
	public static SqlSession getSqlSession() throws IOException{
		
		Reader reader =Resources.getResourceAsReader("config/mybatis-config.xml");
		
		SqlSessionFactory sqlSessionFactory=
				new SqlSessionFactoryBuilder().build(reader);
		SqlSession sqlSession=sqlSessionFactory.openSession(true);
		
		return sqlSession;
		
	}
	public static void printList(List<Object>list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println("<"+(i+1)+">번째 회원"+list.get(i).toString());
			
		}
		System.out.println("\n");
	}

}
